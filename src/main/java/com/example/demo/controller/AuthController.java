package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LogInRequest;
import com.example.demo.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication Endpoint")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> getCredentials (@RequestBody LogInRequest logInRequest) {
        String email = logInRequest.getEmail();
        String password = logInRequest.getPassword();
        UsernamePasswordAuthenticationToken UPAT = new UsernamePasswordAuthenticationToken(email, password);
        try {
            Authentication authResult = authenticationManager.authenticate(UPAT);
            String token = jwtService.generateToken(authResult.getName());

            return ResponseEntity.ok(new AuthResponse(token));

        } catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
