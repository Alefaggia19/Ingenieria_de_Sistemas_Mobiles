package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChallengeService {

    private final List<Challenge> challenges = new ArrayList<>();

    public ChallengeService() {
        // Dati finti (mock)
        Task t1 = new Task(1L, "Trova il QR", "Scansiona il QR nel parco", false);
        Task t2 = new Task(2L, "Rispondi alla domanda", "Qual è la statua più famosa?", false);
        Challenge c1 = new Challenge(1L, "Gincana nel Parco", "Mario Rossi",
                "Sfida per esplorare il parco cittadino", List.of(t1, t2));

        challenges.add(c1);
    }

    public List<Challenge> getAllChallenges() {
        return challenges;
    }

    public Challenge getChallengeById(Long id) {
        return challenges.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Challenge createChallenge(Challenge challenge) {
        challenge.setId((long) (challenges.size() + 1));
        challenges.add(challenge);
        return challenge;
    }
}
