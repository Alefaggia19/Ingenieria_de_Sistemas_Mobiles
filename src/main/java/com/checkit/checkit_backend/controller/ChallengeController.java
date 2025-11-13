package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.service.ChallengeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges") // Tutti gli URL partono con /api/challenges
@CrossOrigin(origins = "*")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }
//
//    @GetMapping
//    public List<Challenge> getAllChallenges() {
//        return challengeService.getAllChallenges();
//    }
//
//    @GetMapping("/{id}")
//    public Challenge getChallenge(@PathVariable Long id) {
//        return challengeService.getChallengeById(id);
//    }

//    @PostMapping
//    public Challenge createChallenge(@RequestBody Challenge challenge) {
//        // IMPORTANTE:
//        // Il JSON inviato dal frontend (Android) deve ora includere
//        // l'ID dell'utente che crea la sfida. Esempio:
//        // {
//        //   "name": "Nuova Sfida",
//        //   "description": "Descrizione...",
//        //   "user": { "id": 1 }  <-- QUESTO È FONDAMENTALE
//        //   "tasks": [ ... ]
//        // }
//        //
//        // Il ChallengeService scritto userà questo ID
//        // per associare la sfida all'utente corretto.
//        return challengeService.createChallenge(challenge);
//    }
}