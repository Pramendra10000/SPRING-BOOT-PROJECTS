package com.parammart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf->csrf.disable())

            .sessionManagement(session->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth->auth

                    .requestMatchers(
                            "/api/auth/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**")
                    .permitAll()

                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                    .requestMatchers("/api/manager/**")
                    .hasRole("MANAGER")

                    .requestMatchers("/api/employee/**")
                    .hasRole("EMPLOYEE")

                    .requestMatchers("/api/customer/**")
                    .hasRole("CUSTOMER")
                    
                    .requestMatchers(HttpMethod.GET, "/api/categories/**")
                    .hasAuthority("CATEGORY_READ")

                    .requestMatchers(HttpMethod.POST, "/api/categories/**")
                    .hasAuthority("CATEGORY_CREATE")

                    .requestMatchers(HttpMethod.PUT, "/api/categories/**")
                    .hasAuthority("CATEGORY_UPDATE")

                    .requestMatchers(HttpMethod.DELETE, "/api/categories/**")
                    .hasAuthority("CATEGORY_DELETE")

                    .anyRequest()
                    .authenticated())

            .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService service,
            PasswordEncoder encoder){

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(service);

        provider.setPasswordEncoder(encoder);

        return provider;
    }

}