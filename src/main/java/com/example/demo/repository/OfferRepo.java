package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Offer;

public interface OfferRepo extends JpaRepository<Offer, Long> {
	List<Offer> findByDestination(String destination);
	
	@Query("SELECT o.destination FROM Offer o")
    List<String> findDestinations();
}