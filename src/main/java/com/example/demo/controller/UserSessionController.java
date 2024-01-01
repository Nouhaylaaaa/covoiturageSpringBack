package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserSession;
import com.example.demo.repository.UserSessionRepository;

@CrossOrigin
@RestController
public class UserSessionController {
	 @Autowired
	    private UserSessionRepository userSessionRepository;

	    @GetMapping("/userIds")
	    public List<UserSession> getAllUserIds() {
	        return userSessionRepository.findAllByUserIdNotNull();
	    }
}
