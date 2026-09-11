package com.parammart.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // =====================================================
            // CORS
            // =====================================================
            .cors(cors -> {})

            // =====================================================
            // CSRF
            // =====================================================
            .csrf(csrf -> csrf.disable())

            // =====================================================
            // SESSION
            // =====================================================
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // =====================================================
            // AUTHORIZATION
            // =====================================================
            .authorizeHttpRequests(auth -> auth

                // -------------------------------------------------
                // PUBLIC AUTHENTICATION APIs
                // -------------------------------------------------
                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                )
                .permitAll()

                // -------------------------------------------------
                // PUBLIC PRODUCT CATALOG - CATEGORIES
                // -------------------------------------------------
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/categories/**"
                )
                .permitAll()

                // -------------------------------------------------
                // PUBLIC PRODUCT CATALOG - BRANDS
                // -------------------------------------------------
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/brands/**"
                )
                .permitAll()

                // -------------------------------------------------
                // ADMIN APIs
                // -------------------------------------------------
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // -------------------------------------------------
                // MANAGER APIs
                // -------------------------------------------------
                .requestMatchers("/api/manager/**")
                .hasRole("MANAGER")

                // -------------------------------------------------
                // EMPLOYEE APIs
                // -------------------------------------------------
                .requestMatchers("/api/employee/**")
                .hasRole("EMPLOYEE")

                // -------------------------------------------------
                // CUSTOMER APIs
                // -------------------------------------------------
                .requestMatchers("/api/customer/**")
                .hasRole("CUSTOMER")

                // -------------------------------------------------
                // CATEGORY MANAGEMENT
                // -------------------------------------------------
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/categories/**"
                )
                .hasAuthority("CATEGORY_CREATE")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/categories/**"
                )
                .hasAuthority("CATEGORY_UPDATE")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/categories/**"
                )
                .hasAuthority("CATEGORY_DELETE")

                // -------------------------------------------------
                // EVERYTHING ELSE
                // -------------------------------------------------
                .anyRequest()
                .authenticated()
            )

            // =====================================================
            // JWT FILTER
            // =====================================================
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // =========================================================
    // AUTHENTICATION MANAGER
    // =========================================================

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    // =========================================================
    // DAO AUTHENTICATION PROVIDER
    // =========================================================

    @Bean
    DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService service,
            PasswordEncoder encoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(encoder);

        return provider;
    }

    // =========================================================
    // CORS CONFIGURATION
    // =========================================================

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
            List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
            )
        );

        configuration.setAllowedHeaders(
            List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
            "/**",
            configuration
        );

        return source;
    }
}