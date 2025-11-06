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

    // 1. Iniettiamo i Repository (non più ArrayList)
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository; // Ci serve per associare l'autore

    // 2. Usiamo l'iniezione del costruttore
    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Recupera tutte le sfide e le converte in DTO.
     * @return Lista di ChallengeDto
     */
    public List<ChallengeDto> getAllChallenges() {
        return challengeRepository.findAll().stream()
                .map(this::toChallengeDto) // Converte ogni Challenge in ChallengeDto
                .toList();
    }

    /**
     * Recupera una singola sfida tramite ID e la converte in DTO.
     * @param id L'ID della sfida
     * @return Il ChallengeDto o null se non trovato
     */
    public ChallengeDto getChallengeById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElse(null); // Restituisce null se non trovato
        if (challenge == null) {
            return null;
        }
        return toChallengeDto(challenge); // Converte il singolo Challenge
    }

    /**
     * Crea una nuova sfida.
     * Riceve un'entità parziale Challenge, la salva nel DB,
     * e restituisce il DTO della sfida creata.
     * @param challenge L'entità Challenge (dal JSON)
     * @return Il ChallengeDto della sfida salvata
     */
    public ChallengeDto createChallenge(Challenge challenge) {
        // 1. Controlla e associa l'autore (User)
        // (Il JSON in input deve avere almeno { "user": { "id": X } } )
        if (challenge.getUser() == null || challenge.getUser().getId() == null) {
            throw new IllegalArgumentException("La sfida deve avere un autore (user ID)");
        }
        
        User author = userRepository.findById(challenge.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Utente autore non trovato"));

        challenge.setUser(author); // Associa l'utente completo (preso dal DB)

        // 2. Assicura che le task siano collegate alla challenge (relazione bidirezionale)
        // Questo è fondamentale per la cascata JPA (CascadeType.ALL)
        if (challenge.getTasks() != null) {
            for (Task task : challenge.getTasks()) {
                task.setChallenge(challenge); // Collega ogni task alla sua "madre"
            }
        }

        // 3. Salva l'entità completa nel DB
        Challenge savedChallenge = challengeRepository.save(challenge);

        // 4. Converti l'entità salvata in un DTO prima di restituirla
        return toChallengeDto(savedChallenge);
    }


    // --- Metodi Mapper Privati ---

    /**
     * Converte una Entità Challenge in un ChallengeDto.
     * @param challenge L'entità presa dal DB
     * @return Il DTO da inviare al frontend
     */
    private ChallengeDto toChallengeDto(Challenge challenge) {
        ChallengeDto dto = new ChallengeDto();
        dto.setId(challenge.getId());
        dto.setName(challenge.getName());
        dto.setDescription(challenge.getDescription());
        dto.setCreationDate(challenge.getCreationDate());

        // Mappatura "appiattita": da Oggetto User a Stringa authorName
        if (challenge.getUser() != null) {
            dto.setAuthorName(challenge.getUser().getUsername());
        }

        // Mappatura della lista interna: converte List<Task> in List<TaskDto>
        if (challenge.getTasks() != null) {
            dto.setTasks(
                challenge.getTasks().stream()
                    .map(this::toTaskDto) // Chiama il mapper delle task
                    .toList()
            );
        }
        return dto;
    }

    /**
     * Converte una Entità Task in un TaskDto.
     * @param task L'entità Task
     * @return Il TaskDto (versione "pubblica" della task)
     */
    private TaskDto toTaskDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setTaskOrder(task.getTaskOrder());
        dto.setType(task.getType());
        // Nota: non includiamo le risposte (qrAnswer, etc.) nel DTO
        return dto;
    }
}