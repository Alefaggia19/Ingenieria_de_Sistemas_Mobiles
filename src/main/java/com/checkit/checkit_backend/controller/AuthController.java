package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.model.User;
import com.checkit.checkit_backend.repository.UserRepository;
import com.checkit.checkit_backend.service.UserDetailsAuthService;
import com.checkit.checkit_backend.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.checkit.checkit_backend.model.UserSession;
import com.checkit.checkit_backend.repository.UserSessionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsAuthService userDetailsAuthService;
    private final JwtUtil jwtUtil;
    // Added dependencies for registration
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // 1. Added repository for sessions
    private final UserSessionRepository userSessionRepository;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserDetailsAuthService userDetailsAuthService,
                          JwtUtil jwtUtil, 
                          UserRepository userRepository, 
                          PasswordEncoder passwordEncoder,
                        UserSessionRepository userSessionRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsAuthService = userDetailsAuthService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSessionRepository = userSessionRepository;
    }

    // --- LOGIN ENDPOINT ---
    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // 1. Authenticate user using credentials (checks password match)
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            // 2. Load the authenticated UserDetails
            //final UserDetails userDetails = userDetailsAuthService.loadUserByUsername(authRequest.getEmail());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // --- KPI'S LOGIC: REGISTRATION OF A SESSION ---
            // Take the user from the database for associate him to his  session
            User user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Save the session to calcolate la Tasa de Conversión
            userSessionRepository.save(new UserSession(user));
            // ------------------------------------------



            // 3. Generate JWT Token
            final String jwt = jwtUtil.generateToken(userDetails);

            // 4. Return the JWT in the response
            return new AuthResponse(null,jwt, userDetails.getUsername());
        } catch (BadCredentialsException e ){
            return new AuthResponse("El nombre de usuario o la contraseña no es correcto",null,null);
        } catch (Exception e){
            return new AuthResponse("Error inesperado",null,null);
        }

    }

    // --- REGISTER ENDPOINT (NEW) ---
    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        // 1. Check if username already exists
        if (userRepository.findByEmail(registerRequest.email()).isPresent()) {
            return new RegisterResponse("Nombre de usuario ya registrado");


        }

        // 2. Create new User entity
        User newUser = new User();
        newUser.setEmail(registerRequest.email());
        newUser.setRealname(registerRequest.username());
        // CRITICAL: Always encode passwords before saving to DB
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        // 3. Save to Database
        userRepository.save(newUser);

        return new RegisterResponse(null);
    }
}

// Simple Request/Response DTOs (Records)
record AuthRequest(String email, String password) {
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
record AuthResponse(String errorResponse,String token, String username) {}

record RegisterRequest(String username,String email,String password){}
record RegisterResponse(String errorMessage){}