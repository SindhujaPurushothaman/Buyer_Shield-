package com.buyershield.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyershield.backend.model.FlaggedReview;
import com.buyershield.backend.model.ScanHistory;
import com.buyershield.backend.repository.FlaggedReviewRepository;
import com.buyershield.backend.repository.ScanHistoryRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FlaggedReviewController {

    @Autowired
    private ScanHistoryRepository scanHistoryRepository;

    @Autowired
    private FlaggedReviewRepository flaggedReviewRepository;

    // Get all scans that were flagged suspicious
    @GetMapping("/flagged-reviews")
    public List<ScanHistory> getFlaggedScans() {
        return scanHistoryRepository.findAll().stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsFlagged()))
                .collect(Collectors.toList());
    }

    // Admin submits a verdict on a flagged scan
    @PostMapping("/review-decision")
    public FlaggedReview submitReview(@RequestBody FlaggedReview review) {
        review.setReviewedAt(LocalDateTime.now());
        return flaggedReviewRepository.save(review);
    }
}