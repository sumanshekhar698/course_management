//package com.trumio.course_management.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    // 1. Define the Password Encoder Bean
//    @Bean
//    @Primary
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Value("${app.security.admin.user}")
//    private String adminUser;
//
//    @Value("${app.security.admin.pass}")
//    private String adminPass;
//
//    @Value("${app.security.student.user}")
//    private String studentUser;
//
//    @Value("${app.security.student.pass}")
//    private String studentPassword;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable()) // Disabled for testing with Postman
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/courses/popular").permitAll() // Let anyone see popular courses
//                        .requestMatchers("/api/students/**").hasRole("ADMIN") // Only ADMINs can manage students
//                        .anyRequest().authenticated() // Everything else needs a login
//                )
//                .httpBasic(Customizer.withDefaults()); // Enables the Login Popup
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        // Creating two "In-Memory" users for teaching purposes
////        UserDetails admin = User.withDefaultPasswordEncoder()
////                .username("teacher")
////                .password("admin123")
////                .roles("ADMIN")
////                .build();
////        UserDetails student = User.withDefaultPasswordEncoder()
////                .username("student")
////                .password("pass123")
////                .roles("USER")
////                .build();
//
//
//        // 2. Use the encoder to hash the passwords
//        UserDetails admin = User.builder()
//                .username(adminUser)
//                .password(encoder.encode(adminPass)) // Hashed!
//                .roles("ADMIN")
//                .build();
//
//        UserDetails student = User.builder()
//                .username(studentUser)
//                .password(encoder.encode(studentPassword)) // Hashed!
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, student);
//    }
//}