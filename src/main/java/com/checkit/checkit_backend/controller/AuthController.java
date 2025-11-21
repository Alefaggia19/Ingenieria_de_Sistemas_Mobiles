package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.model.User;
import com.checkit.checkit_backend.repository.UserRepository;
import com.checkit.checkit_backend.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    // Added dependencies for registration
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserDetailsService userDetailsService, 
                          JwtUtil jwtUtil, 
                          UserRepository userRepository, 
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LOGIN ENDPOINT ---
    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // 1. Authenticate user using credentials (checks password match)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // 2. Load the authenticated UserDetails
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // 3. Generate JWT Token
        final String jwt = jwtUtil.generateToken(userDetails);

        // 4. Return the JWT in the response
        return new AuthResponse(jwt, userDetails.getUsername());
    }

    // --- REGISTER ENDPOINT (NEW) ---
    @PostMapping("/register")
    public String registerUser(@RequestBody AuthRequest registerRequest) {
        // 1. Check if username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        // 2. Create new User entity
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        // CRITICAL: Always encode passwords before saving to DB
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // 3. Save to Database
        userRepository.save(newUser);

        return "User registered successfully";
    }
}

// Simple Request/Response DTOs (Records)
record AuthRequest(String username, String password) {
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
record AuthResponse(String token, String username) {}