package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.ChallengeDto;
import com.checkit.checkit_backend.dto.TaskDto;
import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.model.Task;
import com.checkit.checkit_backend.model.User;
import com.checkit.checkit_backend.repository.ChallengeRepository;
import com.checkit.checkit_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new Challenge linked to the authenticated user.
     */
    public ChallengeDto createChallenge(Challenge challenge, String username) {
        // Fetch the user from DB using the username from JWT
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        challenge.setUser(user); 

        // Link tasks to the challenge to ensure cascade saving works
        if (challenge.getTasks() != null) {
            for (Task task : challenge.getTasks()) {
                task.setChallenge(challenge);
            }
        }
        // Save and convert to DTO
        return toChallengeDto(challengeRepository.save(challenge));
    }

    /**
     * Lists challenges created by the logged-in user.
     */
    public List<ChallengeDto> getMyCreatedChallenges(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        // Convert the list of entities to a list of DTOs
        return challengeRepository.findByUserId(user.getId()).stream()
                .map(this::toChallengeDto)
                .toList();
    }

    /**
     * Lists challenges that the user is following (saved).
     */
    public List<ChallengeDto> getMySavedChallenges(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        // Retrieve saved challenges from the ManyToMany relationship
        return user.getSavedChallenges().stream()
                .map(this::toChallengeDto)
                .toList();
    }

    /**
     * Allows a user to follow (save) a challenge.
     */
    public void followChallenge(Long challengeId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        
        // Add to the list and save user to update the join table
        user.getSavedChallenges().add(challenge);
        userRepository.save(user);
    }

    // --- MAPPERS (Entity -> DTO) ---
    
    private ChallengeDto toChallengeDto(Challenge challenge) {
        ChallengeDto dto = new ChallengeDto();
        dto.setId(challenge.getId());
        dto.setName(challenge.getName());
        dto.setDescription(challenge.getDescription());
        dto.setCreationDate(challenge.getCreationDate());
        
        // Flatten User object to just authorName
        if (challenge.getUser() != null) {
            dto.setAuthorName(challenge.getUser().getUsername());
        }
        // Map tasks if present
        if (challenge.getTasks() != null) {
            dto.setTasks(challenge.getTasks().stream().map(this::toTaskDto).toList());
        }
        return dto;
    }

    private TaskDto toTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setTaskOrder(task.getTaskOrder());
        dto.setType(task.getType());
        return dto;
    }
}