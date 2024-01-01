package com.example.demo.repository;

import com.example.demo.model.UserSession;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    void deleteByUserId(Long userId);
    List<UserSession> findAllByUserIdNotNull();
    void deleteAllByUserIdIsNotNull();
    UserSession findByUserId(Long userId);
}

