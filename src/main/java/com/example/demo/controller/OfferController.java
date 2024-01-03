package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.OfferRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSessionRepository;
import com.example.demo.model.Offer;
import com.example.demo.model.User;


import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/offers")
public class OfferController {
	

	    @Autowired
	    public OfferRepo offerRepository ;
	    @Autowired
	    private UserRepository userRepo;
	    
	    @Autowired
	    private UserSessionRepository userSessionRepository;

	    // Then use this repository to retrieve user session information as needed.

	    
	    @GetMapping("/getOffer")
	    public List<Offer> getAllOffers() {
	        return offerRepository.findAll();
	    }

	    @PostMapping("/addOffer")
	    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
	        Long userId = userSessionRepository.findStoredUserId(); // Retrieve userId from UserSession

	        if (userId != null) {
	            User loggedInUser = userRepo.findById(userId).orElse(null);

	            if (loggedInUser != null) {
	                offer.setUser(loggedInUser); // Set the user for the offer
	                Offer savedOffer = offerRepository.save(offer); // Save the offer with the associated user
	                return ResponseEntity.ok(savedOffer);
	            } else {
	                return ResponseEntity.badRequest().build(); // Return a bad request response if user not found
	            }
	        } else {
	            return ResponseEntity.badRequest().build(); // Return a bad request response if userId not found in session
	        }
	    }
	    @GetMapping("/offersByDestination")
	    public ResponseEntity<?> getOffersByDestination(@RequestParam String destination) {
	        try {
	            List<Offer> offers = offerRepository.findByDestination(destination);
	            return ResponseEntity.ok(offers);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching offers by destination");
	        }
	    }



	

}
