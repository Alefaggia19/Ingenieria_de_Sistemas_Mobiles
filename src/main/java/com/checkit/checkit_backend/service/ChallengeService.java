package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.ChallengeDto;
import com.checkit.checkit_backend.dto.NewChallengeDto;
import com.checkit.checkit_backend.dto.TaskDto;
import com.checkit.checkit_backend.model.Challenge;
import com.checkit.checkit_backend.model.Clue;
import com.checkit.checkit_backend.model.Task;
import com.checkit.checkit_backend.model.User;
import com.checkit.checkit_backend.repository.ChallengeRepository;
import com.checkit.checkit_backend.repository.TaskCompletionRepository;
import com.checkit.checkit_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final TaskCompletionRepository taskCompletionRepository;

    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository, TaskCompletionRepository taskCompletionRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.taskCompletionRepository = taskCompletionRepository;
    }


/**
     * Retrieves the full details of a challenge, 
     * calculating the specific progress for the logged-in user
     */
    public ChallengeDto getChallengeDetail(Long challengeId, String currentUserEmail) {
        // 1. Find the user and the relative Challenge from DB
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        // 2. Use the mapper base for struttural datas
        ChallengeDto dto = toChallengeDto(challenge);

        dto.setAuthor(challenge.getUser().getEmail().equals(currentUserEmail));
        dto.setSaved(user.getSavedChallenges().contains(challenge));
        dto.setCompletedByCount(challenge.getUsersWhoCompleted().size());

        // 3. PUNTO 4: Ordinamento dei task
        // È fondamentale ordinare PRIMA di calcolare lo stato 'locked'
        List<TaskDto> sortedTasks = dto.getTasks().stream()
            .sorted((t1, t2) -> Integer.compare(t1.getTaskOrder(), t2.getTaskOrder()))
            .toList();

        // 4. LOGICA UNIFICATA: Calcolo 'completed' e 'locked'
        boolean previousTaskCompleted = true;

        for (TaskDto taskDto : dto.getTasks()) {
            // Controlliamo se questo specifico compito è stato completato dall'utente 
            boolean isCompleted = user.getCompletedTasks().stream()
                    .anyMatch(t -> t.getId().equals(taskDto.getId()));
            
            taskDto.setCompleted(isCompleted);

            // Gestione dello sblocco progressivo se la sfida è ordinata 
            if (challenge.isOrdered()) {
                // Un task è bloccato se quello precedente non è stato completato
                taskDto.setLocked(!previousTaskCompleted);
                // Aggiorniamo lo stato per il prossimo ciclo del loop
                previousTaskCompleted = isCompleted;
            } else {
                // Se la sfida non è ordinata, nulla è bloccato a priori
                taskDto.setLocked(false);
            }
        

            // Se la sfida è ordinata, il task è locked se il precedente non è finito
            if (challenge.isOrdered()) {
                taskDto.setLocked(!previousTaskCompleted);
                previousTaskCompleted = isCompleted;
            } else {
                taskDto.setLocked(false);
            }
        }
        dto.setTasks(sortedTasks);
        return dto;
    
    }



    /**
 * Creates a new Challenge with its tasks immediately.
 * No draft option is available as per requirements
 */
public ChallengeDto createChallenge(NewChallengeDto dto, String username) {
    // 1. Find the author
    User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // 2. Create the Challenge entity
    Challenge challengeEntity = new Challenge();
    challengeEntity.setName(dto.getName());
    challengeEntity.setDescription(dto.getDescription());
    challengeEntity.setOrdered(dto.isOrdered()); 
    challengeEntity.setUser(user);
    // Salviamo la stringa Base64 direttamente nel campo image dell'entità
    // Nota: Assicurati che nel database il campo sia di tipo TEXT o LOB
    challengeEntity.setImage(dto.getImageBase64());

    // 3. Map and link tasks if present 
    if (dto.getTasks() != null) {
        List<Task> taskEntities = dto.getTasks().stream().map(taskDto -> {
            Task task = new Task();
            task.setName(taskDto.getName());
            task.setType(taskDto.getType());
            task.setTaskOrder(taskDto.getTaskOrder());

            // PUNTO 2: Generazione automatica Risposta per QR e NFC
                if ("QR".equalsIgnoreCase(taskDto.getType()) || "NFC".equalsIgnoreCase(taskDto.getType())) {
                    task.setQrAnswer(UUID.randomUUID().toString());
                    task.setNfcAnswer(task.getQrAnswer()); // Usiamo lo stesso codice univoco
                } else {
                    // PUNTO 1: Per i task TEXT, usiamo la risposta fornita dall'utente
                    task.setTextAnswer(taskDto.getTextAnswer());
                }

            // SALVATAGGIO DELL'INDIZIO (CLUE)
        if (taskDto.getTextClue() != null && !taskDto.getTextClue().isEmpty()) {
            Clue clue = new Clue();
            clue.setTextClue(taskDto.getTextClue());
            clue.setTask(task);
            task.setClues(List.of(clue)); // Collega l'indizio al task
        }
            
            // CRITICAL: Establish the link to the parent challenge
            task.setChallenge(challengeEntity); 
            return task;
        }).collect(java.util.stream.Collectors.toList());
        
        challengeEntity.setTasks(taskEntities);
    }

    // 4. Save to DB (CascadeType.ALL in Challenge entity will handle saving tasks)
    Challenge savedChallenge = challengeRepository.save(challengeEntity);
    return toChallengeDto(savedChallenge);
}

    /**
     * Lists challenges created by the logged-in user.
     */
    public List<ChallengeDto> getMyCreatedChallenges(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        // Convert the list of entities to a list of DTOs
        return challengeRepository.findByUserId(user.getId()).stream()
                .map(this::toChallengeDto)
                .toList();
    }

    /**
     * Lists challenges that the user is following (saved).
     */
    public List<ChallengeDto> getMySavedChallenges(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        // Retrieve saved challenges from the ManyToMany relationship
        return user.getSavedChallenges().stream()
                .map(this::toChallengeDto)
                .toList();
    }

    /**
     * Allows a user to follow (save) a challenge.
     */
    public void followChallenge(Long challengeId, String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));
        
        // Add to the list and save user to update the join table
        user.getSavedChallenges().add(challenge);
        userRepository.save(user);
    }
    

    public void unfollowChallenge(Long challengeId, String username) {
    User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new RuntimeException("Challenge not found"));
    
    // Rimuove la sfida dalla lista dei salvati (ManyToMany)
    user.getSavedChallenges().remove(challenge);
    userRepository.save(user);
}
    
   /** Returns all challenges available in the database.
    * Used for the "Explore" section of the app.
    */
    public List<ChallengeDto> getAllChallenges() {
        return challengeRepository.findAll().stream()
            .map(this::toChallengeDto)
            .toList();
    }

    
    @Transactional // Fondamentale per le operazioni di delete multiple
    public void deleteChallenge(Long challengeId, String currentUserEmail) {
        // 1. Trova la sfida
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        // 2. Trova l'utente che sta facendo la richiesta
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. LOGICA DI SICUREZZA: Verifica se l'utente è l'autore
        if (!challenge.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para eliminar este desafío");
        }

        // SOLUZIONE SQL 23503: Elimina tutti i completamenti associati ai task di questa sfida
        for (Task task : challenge.getTasks()) {
        taskCompletionRepository.deleteByTaskId(task.getId());
        }

        // 4. Elimina (la relazione CascadeType.ALL nel modello Challenge gestirà i task orfani)
        challengeRepository.delete(challenge);
    }

    // --- MAPPERS (Entity -> DTO) ---
    
    private ChallengeDto toChallengeDto(Challenge challenge) {
        ChallengeDto dto = new ChallengeDto();
        dto.setId(challenge.getId());
        dto.setName(challenge.getName());
        dto.setDescription(challenge.getDescription());
        dto.setCreationDate(challenge.getCreationDate());
        dto.setImageBase64(challenge.getImage()); // Mapping saved imagine 
        
        // Flatten User object to just authorName
        if (challenge.getUser() != null) {
            dto.setAuthorName(challenge.getUser().getUsername());
        }
        // Map tasks if present
        // Ordiniamo i task per taskOrder prima di mapparli per garantire la logica del loop sopra
        if (challenge.getTasks() != null) {
            dto.setTasks(challenge.getTasks().stream()
                    .sorted((t1, t2) -> Integer.compare(t1.getTaskOrder(), t2.getTaskOrder()))
                    .map(this::toTaskDto)
                    .toList());
        }
        return dto;
    }


    //Ver el tipo de tarea
    private TaskDto toTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setTaskOrder(task.getTaskOrder());
        dto.setType(task.getType());

        dto.setQrAnswer(task.getQrAnswer());
        dto.setNfcAnswer(task.getNfcAnswer());
        dto.setTextAnswer(task.getTextAnswer());

        // Mappiamo la "Pista" (Clue) come descrizione del compito per la vista dettaglio [cite: 66, 137]
        if (task.getClues() != null && !task.getClues().isEmpty()) {
            dto.setDescription(task.getClues().get(0).getTextClue());
        }
        
        return dto;
    }

     public List<ChallengeDto> getMyInProgressChallenges(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
           var challenges = challengeRepository.findAllChallengesWithCompletionsByUser(user.getId());
        return challenges.stream().map(this::toChallengeDto).toList();

    }
}