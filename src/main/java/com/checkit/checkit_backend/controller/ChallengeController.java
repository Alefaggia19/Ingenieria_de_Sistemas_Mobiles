package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.dto.ChallengeDto;
import com.checkit.checkit_backend.dto.NewChallengeDto;
import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.service.ChallengeService;

import org.springframework.http.ResponseEntity;
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

    //Para implementar el "Listado de desafíos nuevos" descrito en el documento
    @GetMapping
public List<ChallengeDto> getAllChallenges() {
    // This endpoint returns all challenges for the 'Explore' section.
    return challengeService.getAllChallenges();
}

    // LISTARE SFIDE SEGUITE (Saved)
    @GetMapping("/my-saved")
    public List<ChallengeDto> getMySavedChallenges(Principal principal) {
        return challengeService.getMySavedChallenges(principal.getName());
    }

    @GetMapping("/in-progress")
    public List<ChallengeDto> getMyInProgressChallenges(Principal principal){
        return challengeService.getMyInProgressChallenges(principal.getName());
    }

    // SEGUIRE UNA SFIDA
    @PostMapping("/{id}/follow")
    public void followChallenge(@PathVariable Long id, Principal principal) {
        challengeService.followChallenge(id, principal.getName());
    }
    
    //Endpoint per il dettaglio specifico
    @GetMapping("/{id}")
    public ChallengeDto getChallengeDetail(@PathVariable Long id, Principal principal) {
        return challengeService.getChallengeDetail(id, principal.getName());
    }

    // 2. Endpoint per eliminare una sfida dai salvati
    @DeleteMapping("/{id}/follow")
    public ResponseEntity<Void> unfollowChallenge(@PathVariable Long id, Principal principal) {
        challengeService.unfollowChallenge(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    //Delete a challenge
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChallenge(@PathVariable Long id, Principal principal) {
    // principal.getName() restituisce l'email dell'utente loggato
    challengeService.deleteChallenge(id, principal.getName());
    
    // Restituiamo 204 No Content per confermare l'eliminazione senza corpo di risposta
    return ResponseEntity.noContent().build();
    }

}
