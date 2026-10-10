package com.buyershield.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scan_history")
public class ScanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scanId;

    private String tagUid;
    private String scannerType;
    private String deviceId;
    private Double scanLocationLat;
    private Double scanLocationLong;
    private LocalDateTime scanTime;
    private Boolean isFlagged;
    private Double anomalyScore;

    // Getters and setters
    public Integer getScanId() { return scanId; }
    public void setScanId(Integer scanId) { this.scanId = scanId; }

    public String getTagUid() { return tagUid; }
    public void setTagUid(String tagUid) { this.tagUid = tagUid; }

    public String getScannerType() { return scannerType; }
    public void setScannerType(String scannerType) { this.scannerType = scannerType; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public Double getScanLocationLat() { return scanLocationLat; }
    public void setScanLocationLat(Double scanLocationLat) { this.scanLocationLat = scanLocationLat; }

    public Double getScanLocationLong() { return scanLocationLong; }
    public void setScanLocationLong(Double scanLocationLong) { this.scanLocationLong = scanLocationLong; }

    public LocalDateTime getScanTime() { return scanTime; }
    public void setScanTime(LocalDateTime scanTime) { this.scanTime = scanTime; }

    public Boolean getIsFlagged() { return isFlagged; }
    public void setIsFlagged(Boolean isFlagged) { this.isFlagged = isFlagged; }

    public Double getAnomalyScore() { return anomalyScore; }
    public void setAnomalyScore(Double anomalyScore) { this.anomalyScore = anomalyScore; }
}