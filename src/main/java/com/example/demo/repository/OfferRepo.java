package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Offer;

public interface OfferRepo extends JpaRepository<Offer, Long> {
    // Add custom queries here if needed
}