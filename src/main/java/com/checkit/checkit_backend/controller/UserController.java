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

    private final UserDetailsAuthService userDetailsAuthService;
    private final UserDetailsService userDetailsService;
    public UserController(UserDetailsService userDetailService,UserDetailsAuthService userDetailsAuthService){
        this.userDetailsAuthService = userDetailsAuthService;
        this.userDetailsService = userDetailService;
    }

    @GetMapping("/info")
    public UserDTO getOwnUserDetails(Principal principal){
        return userDetailsService.loadUserByUsername(principal.getName());
    }
    @PostMapping("/change-details")
    public BasicResponse changeUserDetails(@RequestBody UserDetailsToChange userDetailsToChange,Principal principal){
        try{
            userDetailsService.changeUserDetails(principal.getName(),
                    userDetailsToChange.realName,
                    userDetailsToChange.password);
            return new BasicResponse(null);
        }catch (Exception e){
            return new BasicResponse(e.getMessage());
        }
    }


    record UserDetailsToChange(String realName,String password){}
    record BasicResponse(String errorMessage){}

}
