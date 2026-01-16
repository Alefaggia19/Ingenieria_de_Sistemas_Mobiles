package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.dto.ChallengeDto;
import com.checkit.checkit_backend.dto.NewChallengeDto;
import com.checkit.checkit_backend.dto.UserDTO;
import com.checkit.checkit_backend.model.User;
import com.checkit.checkit_backend.service.ChallengeService;
import com.checkit.checkit_backend.service.UserDetailsAuthService;
import com.checkit.checkit_backend.service.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;



@RestController
@RequestMapping("/api/user") // Tutti gli URL partono con /api/challenges
@CrossOrigin(origins = "*")
public class UserController {

    private final UserDetailsService userDetailsAuthService;

    public UserController(UserDetailsService userDetailService){
        this.userDetailsAuthService = userDetailService;
    }

    @GetMapping("/info")
    public UserDTO getOwnUserDetails(Principal principal){
        return userDetailsAuthService.loadUserByUsername(principal.getName());
    }

}
