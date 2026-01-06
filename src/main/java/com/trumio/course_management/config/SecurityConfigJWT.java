package com.trumio.course_management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity // <--- Add this!
@Order(1)
public class SecurityConfigJWT {

    @Value("${app.security.admin.user:admin}")
    private String adminUser;

    @Value("${app.security.admin.pass:admin123}")
    private String adminPass;

    @Value("${app.security.student.user:admin}")
    private String studentUser;

    @Value("${app.security.student.pass:admin123}")
    private String studentPass;




/*

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                // JWT is stateless; we don't want Spring to create sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow login/register
                        .anyRequest().authenticated()
                );

        // Add our custom JWT Filter before the standard one
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/

    @Order(1)
    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.securityMatcher("/api/**") // <--- THIS IS THE FIX. It limits this chain's scope.
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // 1. Whitelist Swagger and Auth
                        .requestMatchers(
                                "/api/auth/**"
//                                ,"/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/swagger-resources/**",
//                                "/webjars/**"
                        ).permitAll()


                        // 2. Role-Based Access Control
                        // Only ADMINs can touch Student data
                        .requestMatchers("/api/students/**").hasRole("ADMIN")

                        // Only ADMINs can Create/Delete courses
                        .requestMatchers(HttpMethod.POST, "/api/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")

                        // Both ADMIN and STUDENT can see courses
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "STUDENT")

                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


/*
    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**") // ONLY apply this config to URLs starting with /api/
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/students/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "STUDENT")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    }*/

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createSecurityScheme()));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }


}