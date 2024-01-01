package com.example.demo.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.loginDto;
import com.example.demo.model.User;
import com.example.demo.model.UserSession;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSessionRepository;

import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
public class UserController {
	 @Autowired
	    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserSessionRepository userSessionRepo;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userRepo.save(user);

        // Create a user session for the registered user
        UserSession userSession = new UserSession(savedUser,LocalDateTime.now());
        userSession.setUserId(savedUser.getId());
        userSession.setLoginTime(LocalDateTime.now());
        userSessionRepo.save(userSession);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email) {
    	System.out.println("Received email for login: " + email); // Add this line for debugging
        User user = userRepo.findByEmail(email);
        if (user != null) {
            UserSession userSession = new UserSession(user, LocalDateTime.now());
            userSessionRepo.save(userSession);
            return ResponseEntity.ok("User session created successfully for email: " + email);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
        }
    }

    @Transactional
    @DeleteMapping("/logout")
    public String logout() {
    	userSessionRepo.deleteAllByUserIdIsNotNull();
        return "All user IDs deleted successfully";
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle exception (e.g., return a default or error password)
        }
        return null;
    }
    
    @GetMapping("/userName")
    public ResponseEntity<?> getUserNameBySessionId() {
    	Long loggedInUserId = 1L; // Replace this with the actual logged-in user ID or retrieve it from your session mechanism

        UserSession loggedInUserSession = userSessionRepository.findByUserId(loggedInUserId);

        if (loggedInUserSession != null) {
            User loggedInUser = userRepo.findById(loggedInUserSession.getUserId()).orElse(null);
            
            if (loggedInUser != null) {
                // Return user's name
                return ResponseEntity.ok(loggedInUser.getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User session not found");
        }
        }
    

    @GetMapping("/userEmail")
    public ResponseEntity<?> getUserEmailBySessionId() {
     	Long loggedInUserId = 1L; // Replace this with the actual logged-in user ID or retrieve it from your session mechanism

        UserSession loggedInUserSession = userSessionRepository.findByUserId(loggedInUserId);

        if (loggedInUserSession != null) {
            User loggedInUser = userRepo.findById(loggedInUserSession.getUserId()).orElse(null);
            
            if (loggedInUser != null) {
                // Return user's name
                return ResponseEntity.ok(loggedInUser.getEmail());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User session not found");
        }
        }

    @GetMapping("/userImage")
    public ResponseEntity<?> getUserImageBySessionId() {
     	Long loggedInUserId = 1L; // Replace this with the actual logged-in user ID or retrieve it from your session mechanism

        UserSession loggedInUserSession = userSessionRepository.findByUserId(loggedInUserId);

        if (loggedInUserSession != null) {
            User loggedInUser = userRepo.findById(loggedInUserSession.getUserId()).orElse(null);
            
            if (loggedInUser != null) {
                // Return user's name
                return ResponseEntity.ok(loggedInUser.getImage());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User session not found");
        }
        }

}
