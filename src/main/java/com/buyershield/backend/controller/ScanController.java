package com.buyershield.backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.buyershield.backend.model.ScanHistory;
import com.buyershield.backend.repository.ScanHistoryRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ScanController {

    @Autowired
    private ScanHistoryRepository scanHistoryRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/scan")
    public Map<String, Object> recordScan(@RequestBody ScanHistory scan) {
        scan.setScanTime(LocalDateTime.now());

        // Get this tag's past scan history BEFORE saving the new one
        List<ScanHistory> pastScans = scanHistoryRepository.findByTagUid(scan.getTagUid());

        // Build the request Flask expects
        Map<String, Object> newScanData = new HashMap<>();
        newScanData.put("scanLocationLat", scan.getScanLocationLat());
        newScanData.put("scanLocationLong", scan.getScanLocationLong());
        newScanData.put("scanTime", scan.getScanTime().toString());

        List<Map<String, Object>> historyData = new ArrayList<>();
        for (ScanHistory past : pastScans) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("scanLocationLat", past.getScanLocationLat());
            entry.put("scanLocationLong", past.getScanLocationLong());
            entry.put("scanTime", past.getScanTime().toString());
            historyData.add(entry);
        }

        Map<String, Object> mlRequest = new HashMap<>();
        mlRequest.put("scanHistory", historyData);
        mlRequest.put("newScan", newScanData);

        // Call the Flask ML service
        Map<String, Object> mlResponse;
        try {
            mlResponse = restTemplate.postForObject(
                "http://localhost:5000/predict", mlRequest, Map.class
            );
        } catch (Exception e) {
            mlResponse = new HashMap<>();
            mlResponse.put("suspicious", false);
            mlResponse.put("error", "ML service unavailable");
        }

        boolean suspicious = (boolean) mlResponse.getOrDefault("suspicious", false);
        Double anomalyScore = mlResponse.get("anomaly_score") != null
            ? ((Number) mlResponse.get("anomaly_score")).doubleValue() : null;

        scan.setIsFlagged(suspicious);
        scan.setAnomalyScore(anomalyScore);

        ScanHistory saved = scanHistoryRepository.save(scan);

        Map<String, Object> response = new HashMap<>();
        response.put("scanId", saved.getScanId());
        response.put("status", suspicious ? "suspicious" : "genuine");
        response.put("anomalyScore", anomalyScore);

        return response;
    }

    @GetMapping("/scan-history/{tagUid}")
    public List<ScanHistory> getScanHistory(@PathVariable String tagUid) {
        return scanHistoryRepository.findByTagUid(tagUid);
    }
}