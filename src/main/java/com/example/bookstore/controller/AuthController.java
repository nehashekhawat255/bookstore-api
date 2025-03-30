package com.example.bookstore.controller;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) { // In real-world, use password hashing
                String token = jwtUtil.generateToken(email);
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email, @RequestParam String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(400).body("User already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // In production, use password hashing like BCrypt
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

}
