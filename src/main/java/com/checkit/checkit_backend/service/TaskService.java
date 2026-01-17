package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.TaskDetailDTO;
import com.checkit.checkit_backend.model.*;
import com.checkit.checkit_backend.repository.TaskCompletionRepository;
import com.checkit.checkit_backend.repository.TaskRepository;
import com.checkit.checkit_backend.repository.ChallengeRepository;
import com.checkit.checkit_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ChallengeRepository challengeRepository;
    private final TaskCompletionRepository taskCompletionRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, 
                       ChallengeRepository challengeRepository,
                       TaskCompletionRepository taskCompletionRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.challengeRepository = challengeRepository;
        this.taskCompletionRepository = taskCompletionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Validates the user's response and marks the task as completed.
     */
    public boolean completeTask(Long taskId, String username, String userResponse) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 1. Check if the user has already completed this task
        if (taskCompletionRepository.existsByUserIdAndTaskId(user.getId(), taskId)) {
            return true; // Already done, return success
        }

        // 2. Validate the answer based on the task Type
        boolean isCorrect = false;
        
        // Case-insensitive check for text answers
        if ("TEXT".equalsIgnoreCase(task.getType())) {
            isCorrect = userResponse != null && userResponse.equalsIgnoreCase(task.getTextAnswer());
        } 
        // Exact match required for QR codes
        else if ("QR".equalsIgnoreCase(task.getType())) {
            isCorrect = userResponse != null && userResponse.equals(task.getQrAnswer());
        } 
        // Exact match required for NFC tags
        else if ("NFC".equalsIgnoreCase(task.getType())) {
            isCorrect = userResponse != null && userResponse.equals(task.getNfcAnswer());
        }

        // 3. If correct, save the completion record
        if (isCorrect) {
            TaskCompletion completion = new TaskCompletion(user, task);
            taskCompletionRepository.save(completion);
            return true;
        }
        
        return false; // Answer was incorrect
    }

    // --- Standard CRUD Methods ---
    
    public List<Task> getTasksByChallengeId(Long challengeId) {
        return taskRepository.findByChallengeId(challengeId);
    }

    public TaskDetailDTO getTaskById(Long id,Long userId) {

        var taskDTO  = new TaskDetailDTO();
        var taskEntity = taskRepository.findById(id).orElse(null);
        assert taskEntity != null;
        taskDTO.setChallengeID(taskEntity.getChallenge().getId());
        taskDTO.setId(taskEntity.getId());
        taskDTO.setName(taskEntity.getName());
        taskDTO.setTaskOrder(taskEntity.getTaskOrder());
        taskDTO.setTextClue(taskEntity.getClues().stream().map(Clue::getTextClue).toList());
        taskDTO.setnCompletions(taskCompletionRepository.countByTaskId(id));
        taskDTO.setCompleted(taskCompletionRepository.existsByUserIdAndTaskId(userId,id));
        taskDTO.setType(taskEntity.getType());

        return taskDTO;

    }

    public Task addTaskToChallenge(Long challengeId, Task task) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));
        task.setChallenge(challenge);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setName(updatedTask.getName());
            task.setTaskOrder(updatedTask.getTaskOrder());
            task.setType(updatedTask.getType());
            // Update answers
            task.setQrAnswer(updatedTask.getQrAnswer());
            task.setNfcAnswer(updatedTask.getNfcAnswer());
            task.setTextAnswer(updatedTask.getTextAnswer());
            return taskRepository.save(task);
        }).orElse(null);
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}