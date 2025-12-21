package com.PSNS.Bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
//                .requestMatchers("/users", "/reports").hasRole("ADMIN")
//                .requestMatchers("/dashboard", "/transactions", "/transfer")
//                    .hasAnyRole("ADMIN", "CUSTOMER")
//                .anyRequest().authenticated()
//            )
//            .formLogin(login -> login
//                .loginPage("/login")
//                .loginProcessingUrl("/doLogin")
//                .defaultSuccessUrl("/dashboard", true)
//                .failureUrl("/login?error=true")
//            )
//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//            );
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService users() {
//        return new InMemoryUserDetailsManager(
//            User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build(),
//            User.withUsername("user").password("{noop}user123").roles("CUSTOMER").build()
//        );
//    }
}



