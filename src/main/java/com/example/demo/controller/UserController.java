package com.example.demo.controller;

import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        System.out.println("Received email for login: " + email);
       

        User user = userRepo.findByEmail(email);
        if (user != null) {
            String storedHashedPassword = user.getPassword(); // Retrieve stored hashed password

            String hashedEnteredPassword = hashPassword(password);
            System.out.println("password: " + hashedEnteredPassword);// Hash the entered password

            if (storedHashedPassword.equals(hashedEnteredPassword)) {
                UserSession userSession = new UserSession(user, LocalDateTime.now());
                userSessionRepo.save(userSession);
                return ResponseEntity.ok("User session created successfully for email: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password for email: " + email);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
        }
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
    public static String decodePassword(String hashedPassword) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(hashedPassword);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return null;
    }
    @Transactional
    @DeleteMapping("/logout")
    public String logout() {
    	userSessionRepo.deleteAllByUserIdIsNotNull();
        return "All user IDs deleted successfully";
    }

    
    @GetMapping("/userName")
    public ResponseEntity<?> getUserNameBySessionId() {
        Long userId = userSessionRepository.findStoredUserId();

        if (userId != null) {
            User loggedInUser = userRepo.findById(userId).orElse(null);

            if (loggedInUser != null) {
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
    	  Long userId = userSessionRepository.findStoredUserId();

          if (userId != null) {
              User loggedInUser = userRepo.findById(userId).orElse(null);

              if (loggedInUser != null) {
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
    	  Long userId = userSessionRepository.findStoredUserId();

          if (userId != null) {
              User loggedInUser = userRepo.findById(userId).orElse(null);

              if (loggedInUser != null) {
                  return ResponseEntity.ok(loggedInUser.getImage());
              } else {
                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
              }
          } else {
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User session not found");
          }
      }

}
