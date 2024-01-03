package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Offer;

public interface OfferRepo extends JpaRepository<Offer, Long> {
	List<Offer> findByDestination(String destination);
}