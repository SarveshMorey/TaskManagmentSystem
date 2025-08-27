package com.wipro.springboot_tms.controller;

import com.wipro.springboot_tms.entity.User;
import com.wipro.springboot_tms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Email already exists"));
        }
        User saved = userRepository.save(user);
        return ResponseEntity.ok(Map.of("userId", saved.getUserId(), "username", saved.getUsername(), "email", saved.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        Optional<User> u = userRepository.findByEmailAndPassword(email, password);
        if (u.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid credentials"));
        }
        User user = u.get();
        return ResponseEntity.ok(Map.of("userId", user.getUserId(), "username", user.getUsername(), "email", user.getEmail()));
    }
}
