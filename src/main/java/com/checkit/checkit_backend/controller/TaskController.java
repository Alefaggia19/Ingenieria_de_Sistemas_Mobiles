package com.checkit.checkit_backend.controller;

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

    // Questi endpoint restano validi per operare su task singole
    // (presupponendo di conoscere già l'ID della task)

    // GET /api/tasks/5 -> Restituisce la task con ID 5
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
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