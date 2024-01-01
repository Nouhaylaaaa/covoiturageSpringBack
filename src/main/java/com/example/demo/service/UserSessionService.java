package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.repository.UserSessionRepository;

@Service
public class UserSessionService {
	private final UserSessionRepository userSessionRepository;

    @Autowired
    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    
}
