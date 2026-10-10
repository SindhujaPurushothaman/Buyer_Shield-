package com.buyershield.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buyershield.backend.model.FlaggedReview;

public interface FlaggedReviewRepository extends JpaRepository<FlaggedReview, Integer> {
}