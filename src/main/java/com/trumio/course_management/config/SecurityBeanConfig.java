package com.trumio.course_management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityBeanConfig {

    @Value("${app.security.admin.user}")
    private String adminUser;

    @Value("${app.security.admin.pass}")
    private String adminPass;

    @Value("${app.security.student.user}")
    private String studentUser;

    @Value("${app.security.student.pass}")
    private String studentPass;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Creating two "In-Memory" users for teaching purposes
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("teacher")
//                .password("admin123")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails student = User.withDefaultPasswordEncoder()
//                .username("student")
//                .password("pass123")
//                .roles("USER")
//                .build();


        // 2. Use the encoder to hash the passwords

        UserDetails admin = User.builder()
                .username(adminUser)
                .password(encoder.encode(adminPass))
                .roles("ADMIN") // Spring adds "ROLE_" prefix
                .build();

        UserDetails student = User.builder()
                .username(studentUser)
                .password(encoder.encode(studentPass))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }


    // You need this bean so your AuthController can perform the login

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}