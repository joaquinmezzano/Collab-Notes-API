package com.collabnotes.notes_api.controller;

import com.collabnotes.notes_api.model.User;
import com.collabnotes.notes_api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // POST /users -> Create users
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    // GET /users -> Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // GET /users/{id} -> Get the user from the id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
