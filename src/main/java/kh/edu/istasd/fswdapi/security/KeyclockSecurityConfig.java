package kh.edu.istasd.fswdapi.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeyclockSecurityConfig {

    String ROLE_ADMIN = "ADMIN";
    String ROLE_USER = "USER";
    String ROLE_STAFF = "STAFF";
    String ROLE_CUSTOMER = "CUSTOMER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(endpoints->
                endpoints
                        // Swagger and OpenAPI documentation - MUST come first
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Public endpoints
                        .requestMatchers("api/v1/media/**").permitAll()
                        .requestMatchers("api/v1/auth/**").permitAll()
                        .requestMatchers("/ws-chat/**").permitAll()
                        .requestMatchers("/api/v1/messages/history/**").permitAll()

                        // Protected endpoints
                        .requestMatchers(HttpMethod.POST,"api/v1/customers/**").hasAnyRole(ROLE_CUSTOMER, ROLE_ADMIN, ROLE_USER)
                        .requestMatchers(HttpMethod.PATCH,"api/v1/customers/**").hasAnyRole(ROLE_STAFF, ROLE_ADMIN, ROLE_USER)
                        .requestMatchers(HttpMethod.DELETE,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_USER)
                        .requestMatchers(HttpMethod.GET,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_CUSTOMER, ROLE_STAFF, ROLE_USER)
                        .requestMatchers("api/v1/accounts/**").hasAnyRole(ROLE_ADMIN, ROLE_CUSTOMER, ROLE_STAFF, ROLE_USER)

                        // All other requests require authentication
                        .anyRequest()
                        .authenticated()
        );

        // Disable form login because we're using OAuth2/JWT
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        // Set OAuth2 resource server with JWT
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverterForKeyCloak())
                )
        );

        // Disable CSRF for stateless API
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Make API stateless
        httpSecurity.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeyCloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realMAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realMAccess.get("roles");
            log.info("roles: {}", roles);

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }

}