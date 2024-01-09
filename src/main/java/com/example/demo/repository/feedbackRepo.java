package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Feedback;

public interface feedbackRepo extends JpaRepository<Feedback, Long>{
	List<Feedback> findByOfferId(Long id_offer);
}
