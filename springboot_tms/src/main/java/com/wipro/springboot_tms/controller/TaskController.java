package com.wipro.springboot_tms.controller;

import com.wipro.springboot_tms.dto.TaskRequest;
import com.wipro.springboot_tms.dto.TaskResponse;
import com.wipro.springboot_tms.entity.Status;
import com.wipro.springboot_tms.entity.Task;
import com.wipro.springboot_tms.entity.User;
import com.wipro.springboot_tms.repository.TaskRepository;
import com.wipro.springboot_tms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private TaskResponse toDto(Task t) {
        return new TaskResponse(
                t.getTaskId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus().name(),
                t.getDueDate(),
                t.getUser() != null ? t.getUser().getUserId() : null
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer id) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return ResponseEntity.ok(toDto(t));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest req) {
        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        if (req.getStatus() != null) {
            task.setStatus(Status.valueOf(req.getStatus()));
        }
        task.setDueDate(req.getDueDate());

        if (req.getUserId() != null) {
            User user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + req.getUserId()));
            task.setUser(user);
        }

        Task saved = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer id, @RequestBody TaskRequest req) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (req.getTitle() != null) task.setTitle(req.getTitle());
        if (req.getDescription() != null) task.setDescription(req.getDescription());
        if (req.getStatus() != null) task.setStatus(Status.valueOf(req.getStatus()));
        if (req.getDueDate() != null) task.setDueDate(req.getDueDate());

        if (req.getUserId() != null) {
            User user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + req.getUserId()));
            task.setUser(user);
        }

        Task saved = taskRepository.save(task);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasksByUser(@PathVariable Integer userId) {
        List<TaskResponse> tasks = taskRepository.findByUserUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(tasks);
    }
}
