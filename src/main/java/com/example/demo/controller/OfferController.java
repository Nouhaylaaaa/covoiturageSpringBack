package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.OfferRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserSessionRepository;
import com.example.demo.model.Offer;
import com.example.demo.model.User;
import com.example.demo.model.UserSession;

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
	        Long loggedInUserId = 1L; // Replace this with the actual logged-in user ID or retrieve it from your session mechanism

	        UserSession loggedInUserSession = userSessionRepository.findByUserId(loggedInUserId);

	        if (loggedInUserSession != null) {
	            User loggedInUser = userRepo.findById(loggedInUserSession.getUserId()).orElse(null);

	            if (loggedInUser != null) {
	                offer.setUser(loggedInUser); // Set the user for the offer
	                Offer savedOffer = offerRepository.save(offer); // Save the offer with the associated user
	                return ResponseEntity.ok(savedOffer);
	            } else {
	                return ResponseEntity.badRequest().build(); // Return a bad request response if user not found
	            }
	        } else {
	            return ResponseEntity.badRequest().build(); // Return a bad request response if user session not found
	        }
	    }


	    @GetMapping("/{id}")
	    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
	        return offerRepository.findById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer updatedOffer) {
	        return offerRepository.findById(id)
	                .map(existingOffer -> {
	                    existingOffer.setDestination(updatedOffer.getDestination());
	                    existingOffer.setStartingPoint(updatedOffer.getStartingPoint());
	                    existingOffer.setDateDeparture(updatedOffer.getDateDeparture());
	                    existingOffer.setNumberOfPassengers(updatedOffer.getNumberOfPassengers());
	                    // Update other fields as needed

	                    Offer savedOffer = offerRepository.save(existingOffer);
	                    return ResponseEntity.ok(savedOffer);
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteOffer(@PathVariable Long id) {
	        return offerRepository.findById(id)
	                .map(offer -> {
	                    offerRepository.delete(offer);
	                    return ResponseEntity.ok().build();
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }

}
