package com.wipro.springboot_tms.controller;

import com.wipro.springboot_tms.entity.User;
import com.wipro.springboot_tms.repository.UserRepository;
import com.wipro.springboot_tms.dto.UserResponse;
import com.wipro.springboot_tms.dto.UserRequest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getUserId(), u.getUsername(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserResponse(u.getUserId(), u.getUsername(), u.getEmail()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(saved.getUserId(), saved.getUsername(), saved.getEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Integer id, @RequestBody UserRequest dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        User saved = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(saved.getUserId(), saved.getUsername(), saved.getEmail()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
