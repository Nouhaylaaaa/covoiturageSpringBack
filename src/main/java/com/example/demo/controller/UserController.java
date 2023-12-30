package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/register")
	public User Register(@RequestBody User user) {
		return userRepo.save(user);
	}
	
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		
		return userRepo.findByEmailAndPassword(user.email, user.password);
	}

}
