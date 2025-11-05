package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*") // Permette chiamate da app esterne
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeService.getAllChallenges();
    }

    @GetMapping("/{id}")
    public Challenge getChallenge(@PathVariable Long id) {
        return challengeService.getChallengeById(id);
    }

    @PostMapping
    public Challenge createChallenge(@RequestBody Challenge challenge) {
        return challengeService.createChallenge(challenge);
    }
}
