package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.dto.TaskDetailDTO;
import com.checkit.checkit_backend.model.Clue;
import com.checkit.checkit_backend.model.Task;
import com.checkit.checkit_backend.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api") // Nota: Ho reso il mapping di base più generico
@CrossOrigin(origins = "*") // Aggiunto per coerenza
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // NUOVO ENDPOINT:
    // GET /api/challenges/1/tasks -> Restituisce tutte le task per la sfida 1
    @GetMapping("/challenges/{challengeId}/tasks")
    public List<Task> getTasksForChallenge(@PathVariable Long challengeId) {
        return taskService.getTasksByChallengeId(challengeId);
    }

    // ENDPOINT MODIFICATO:
    // POST /api/challenges/1/tasks -> Aggiunge una nuova task (dal RequestBody) alla sfida 1
    @PostMapping("/challenges/{challengeId}/tasks")
    public Task addTaskToChallenge(@PathVariable Long challengeId, @RequestBody Task task) {
        return taskService.addTaskToChallenge(challengeId, task);
    }
    

    //We expose the API to complete tasks. It is essential for supporting validation via QR and NFC
    @PostMapping("/tasks/{id}/complete")
public ResponseEntity<String> completeTask(@PathVariable Long id, 
                                           @RequestBody String userResponse, 
                                           Principal principal) {
    // principal.getName() ottiene lo username dal JWT
    boolean success = taskService.completeTask(id, principal.getName(), userResponse);
    
    if (success) {
        return ResponseEntity.ok("Task completata con successo!");
    } else {
        return ResponseEntity.badRequest().body("Risposta errata o già completata.");
    }
}

    // GET /api/tasks/5 -> Restituisce la task con ID 5
    @GetMapping("/tasks/{id}")
    public TaskDetailDTO getTaskById(@PathVariable Long id) {
        var taskDTO  = new TaskDetailDTO();
        var taskEntity = taskService.getTaskById(id);
        taskDTO.setChallengeID(taskEntity.getChallenge().getId());
        taskDTO.setId(taskEntity.getId());
        taskDTO.setName(taskEntity.getName());
        taskDTO.setTaskOrder(taskEntity.getTaskOrder());
        taskDTO.setTextClue(taskEntity.getClues().stream().map(Clue::getTextClue).toList());
        //taskDTO.setnCompletions();
        return taskDTO;
    }

    // PUT /api/tasks/5 -> Aggiorna la task 5
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    // DELETE /api/tasks/5 -> Elimina la task 5
    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Long id) {
        boolean removed = taskService.deleteTask(id);
        return removed ? "Task eliminata con successo." : "Task non trovata.";
    }
}