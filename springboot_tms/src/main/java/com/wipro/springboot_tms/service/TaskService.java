package com.wipro.springboot_tms.service;

import com.wipro.springboot_tms.entity.Task;
import com.wipro.springboot_tms.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task updateTask(Integer id, Task updated) {
        updated.setTaskId(id);
        return taskRepository.save(updated);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
