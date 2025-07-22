package kh.edu.istasd.fswdapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    String ROLE_ADMIN = "ADMIN";
    String ROLE_USER = "USER";
    String ROLE_STAFF = "STAFF";
    String ROLE_CUSTOMER = "CUSTOMER";

//    configure user to change the password that generate by spring
    //{noop} is default prefix that is used if you don't want to create bean PasswordEndcoder
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles(ROLE_ADMIN).build();
//        manager.createUser(admin);
//
//        UserDetails staff = User.withUsername("staff").password(passwordEncoder.encode("staff")).roles(ROLE_USER).build();
//        manager.createUser(staff);
//
//        UserDetails customer = User.withUsername("customer").password(passwordEncoder.encode("customer")).roles(ROLE_CUSTOMER).build();
//        manager.createUser(customer);
//        return manager;
//    }

    // is used to handle user on database when we login
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //TODO
        // make all endpoints secured
        httpSecurity.authorizeHttpRequests(endpoints->
                endpoints
                        .requestMatchers(HttpMethod.POST,"api/v1/customers/**").hasAnyRole(ROLE_CUSTOMER, ROLE_ADMIN,ROLE_USER)
                        .requestMatchers(HttpMethod.PATCH,"api/v1/customers/**").hasAnyRole(ROLE_STAFF, ROLE_ADMIN,ROLE_USER)
                        .requestMatchers(HttpMethod.DELETE,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN,ROLE_USER)
                        .requestMatchers(HttpMethod.GET,"api/v1/customers/**").hasAnyRole(ROLE_ADMIN, ROLE_CUSTOMER,ROLE_STAFF, ROLE_USER)
                        .requestMatchers("api/v1/accounts/**").hasAnyRole(ROLE_ADMIN, ROLE_CUSTOMER,ROLE_STAFF,ROLE_USER)
                        .anyRequest()
                        .authenticated()
        );
        // Disable form login of web because we want to create form ourselves
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        //set security (httpBasic) machanism http basic Authenticaion to enable default form
        httpSecurity.httpBasic(Customizer.withDefaults());

        // CSRF(Cross-site Request Forgery) common token is concept of automatically generate token to spring security(spring security reserve only csrf token) when login
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // make api is stateless
        httpSecurity.sessionManagement(session->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
