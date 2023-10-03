package com.financemanagementwebapp.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String jwtToken;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;


    public JwtResponse(String jwtToken, String refreshToken, Long id, String username, String email, String firstName, String lastName, String role) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
