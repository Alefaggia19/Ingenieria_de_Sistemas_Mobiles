package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.model.Task;
import com.checkit.checkit_backend.repository.TaskRepository;
import com.checkit.checkit_backend.repository.ChallengeRepository;
import com.checkit.checkit_backend.model.Challenge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    // 1. Iniettiamo i Repository
    private final TaskRepository taskRepository;
    private final ChallengeRepository challengeRepository;

    public TaskService(TaskRepository taskRepository, ChallengeRepository challengeRepository) {
        this.taskRepository = taskRepository;
        this.challengeRepository = challengeRepository;
        // Non servono dati finti
    }

    // NUOVO: Questo metodo ora ha più senso
    public List<Task> getTasksByChallengeId(Long challengeId) {
        return taskRepository.findByChallengeId(challengeId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElse(null);
    }

    // MODIFICATO: Per aggiungere una task, dobbiamo sapere A QUALE SFIDA appartiene
    public Task addTaskToChallenge(Long challengeId, Task task) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Sfida non trovata"));
        
        task.setChallenge(challenge); // Collega la task alla sfida
        
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElse(null);

        if (existingTask != null) {
            existingTask.setName(updatedTask.getName());
            existingTask.setTaskOrder(updatedTask.getTaskOrder());
            existingTask.setType(updatedTask.getType());
            existingTask.setQrAnswer(updatedTask.getQrAnswer());
            existingTask.setNfcAnswer(updatedTask.getNfcAnswer());
            existingTask.setTextAnswer(updatedTask.getTextAnswer());
            // Non aggiorniamo l'ID della challenge
            
            return taskRepository.save(existingTask);
        }
        return null; // Task non trovata
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false; // Task non trovata
    }
    
    // Il vecchio 'getAllTasks()' non ha molto senso, 
    // ma lo lasciamo se serve per debug
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}