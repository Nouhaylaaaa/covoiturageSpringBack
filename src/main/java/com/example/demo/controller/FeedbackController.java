package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Feedback;
import com.example.demo.model.Offer;
import com.example.demo.model.User;
import com.example.demo.repository.OfferRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSessionRepository;
import com.example.demo.repository.feedbackRepo;

@CrossOrigin
@RestController
@RequestMapping("/feed")
public class FeedbackController {
	@Autowired
    public OfferRepo offerRepository ;
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserSessionRepository userSessionRepository;
	 @Autowired
	    private feedbackRepo feedbackRepository;
	    
	    @PostMapping("/addFeedback")
	    public ResponseEntity<?> submitFeedback(@RequestBody Feedback feedback, @RequestParam Long id_offer) {
	        Long userId = userSessionRepository.findStoredUserId();
	        // Retrieve userId from UserSession

	        if (userId != null) {
	            User loggedInUser = userRepo.findById(userId).orElse(null);
	            Offer offer = offerRepository.findById(id_offer).orElse(null);
	           
	            if (loggedInUser != null && offer != null) {
	                feedback.setUser(loggedInUser);
	                feedback.setOffer(offer); // Set the user for the offer

	                Feedback savedFeedback = feedbackRepository.save(feedback); // Save the feedback with the associated user and offer

	                return ResponseEntity.ok(savedFeedback);
	            } else {
	                return ResponseEntity.badRequest().build(); // Return a bad request response if user or offer not found
	            }
	        } else {
	            return ResponseEntity.badRequest().build(); // Return a bad request response if userId not found in session
	        }
	    }
	    @GetMapping("/getAllfeedback")
	    public List<Feedback> getAllFeedbacks() {
	        return feedbackRepository.findAll();
	    }
	    @GetMapping("/getfeedback")
	    public List<Feedback> getFeedbackByOfferId(@RequestParam Long id_offer) {
	        // Implement logic to fetch feedback based on the offer ID
	        // For example:
	        return feedbackRepository.findByOfferId(id_offer);
	    }



}
