package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private List<Task> tasks = new ArrayList<>();

    public TaskService() {
        // dati di esempio
        tasks.add(new Task(1L, "Scansiona QR", "Trova il codice QR nel parco", false));
        tasks.add(new Task(2L, "NFC Challenge", "Avvicina il dispositivo NFC", true));
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

        public Task addTask(Task task) {
        // genera un ID finto (incrementale)
        Long newId = (long) (tasks.size() + 1);
        task.setId(newId);
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setTitle(updatedTask.getTitle());
                t.setDescription(updatedTask.getDescription());
                t.setCompleted(updatedTask.isCompleted());
                return t;
            }
        }
        return null;
    }

    public boolean deleteTask(Long id) {
        return tasks.removeIf(t -> t.getId().equals(id));
    }


}
