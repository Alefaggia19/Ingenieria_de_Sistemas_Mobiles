package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.dto.ChallengeDto;
import com.checkit.checkit_backend.dto.NewChallengeDto;
import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.service.ChallengeService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;

@RestController
@RequestMapping("/api/challenges") // Tutti gli URL partono con /api/challenges
@CrossOrigin(origins = "*")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }


    //CREARE CHALLENGE
    @PostMapping
    public ChallengeDto createChallenge(@RequestBody NewChallengeDto challenge, Principal principal) {
        // 'principal.getName()' restituisce lo username dal Token JWT
        return challengeService.createChallenge(challenge, principal.getName());
    }

    // LISTARE PROPRIE SFIDE (Create)
    @GetMapping("/my-created")
    public List<ChallengeDto> getMyCreatedChallenges(Principal principal) {
        return challengeService.getMyCreatedChallenges(principal.getName());
    }

    // LISTARE SFIDE SEGUITE (Saved)
    @GetMapping("/my-saved")
    public List<ChallengeDto> getMySavedChallenges(Principal principal) {
        return challengeService.getMySavedChallenges(principal.getName());
    }

    // SEGUIRE UNA SFIDA
    @PostMapping("/{id}/follow")
    public void followChallenge(@PathVariable Long id, Principal principal) {
        challengeService.followChallenge(id, principal.getName());
    }
    
    // ... (altri metodi GET publici)
}
