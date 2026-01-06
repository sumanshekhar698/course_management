//package com.trumio.course_management.controller;
//
//import com.trumio.course_management.dto.AuthRequest;
//import com.trumio.course_management.dto.AuthResponse;
//import com.trumio.course_management.entities.User;
//import com.trumio.course_management.dao.UserDao;
//import com.trumio.course_management.security.JwtUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/auth")
//public class AuthControllerV1 {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            log.error("Invalid credentials for user: {}", authRequest.getUsername());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new AuthResponse(null, null, "Invalid username or password"));
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        log.info("User {} logged in successfully", authRequest.getUsername());
//        return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), "Login successful"));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
//        if (userDao.existsByUsername(authRequest.getUsername())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new AuthResponse(null, null, "Username already exists"));
//        }
//
//        User user = new User();
//        user.setUsername(authRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
//        user.setRole("ROLE_USER"); // Default role
//        user.setEnabled(true);
//
//        userDao.save(user);
//        log.info("New user registered: {}", authRequest.getUsername());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new AuthResponse(null, user.getUsername(), "User registered successfully"));
//    }
//}
