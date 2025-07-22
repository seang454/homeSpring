package kh.edu.istasd.fswdapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //TODO
        // make all endpoints secured
        httpSecurity.authorizeHttpRequests(endpoints->
                endpoints.anyRequest().authenticated()
        );
        // Disable form login of web because we want to create form ourselves
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        //set security (httpBasic) machanism http basic Authenticaion to enable default form
        httpSecurity.httpBasic(Customizer.withDefaults());

        // CSRF(Cross-site Request Forgery) common token is concept of automatically generate token to spring security(spring security reserve only csrf token) when login
        httpSecurity.csrf(token->token.disable());
        // make api is stateless
        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
