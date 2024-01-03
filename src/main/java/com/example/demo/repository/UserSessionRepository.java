package com.example.demo.repository;

import com.example.demo.model.UserSession;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    void deleteByUserId(Long userId);
    List<UserSession> findAllByUserIdNotNull();
    void deleteAllByUserIdIsNotNull();
    UserSession findByUserId(Long userId);
    @Query("SELECT u FROM UserSession u WHERE u.id = :id") // Specify the query explicitly
    UserSession findUserSessionById(Long id);
    
    @Query("SELECT u.userId FROM UserSession u")
    Long findStoredUserId();
  
}

