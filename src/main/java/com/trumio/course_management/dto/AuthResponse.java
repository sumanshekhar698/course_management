package com.trumio.course_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String message;

    public AuthResponse(String token) {
        this.token = token;
    }
}
