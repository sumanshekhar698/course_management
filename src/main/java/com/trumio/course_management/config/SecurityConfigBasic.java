package com.trumio.course_management.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@Order(2) // Lower priority
public class SecurityConfigBasic {

    @Order(2)
    @Bean
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {

/*        http.csrf(csrf -> csrf.disable()) // Disabled for testing with Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/courses/popular").permitAll() // Let anyone see popular courses
                        .requestMatchers("/api/students/**").hasRole("ADMIN") // Only ADMINs can manage students
                        .anyRequest().authenticated() // Everything else needs a login
                )
                .httpBasic(Customizer.withDefaults()); // Enables the Login Popup*/


        http.csrf(csrf -> csrf.disable())
                // This chain catches everything that NOT /api/**
                .authorizeHttpRequests(auth -> auth
                        // 1. Whitelist - No login required for these
                        .requestMatchers(
//                                "/api/auth/**",
//                                "/api/courses/popular",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        // 2. Role-based rules
                        .requestMatchers("/api/students/**").hasRole("ADMIN")
                        // 3. Catch-all - Everything else triggers the Login Popup
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }



    /*
    @Bean
    @Order(2)
    public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin-console/**") // This chain handles the dashboard
                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole("ADMIN"))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }*/
}