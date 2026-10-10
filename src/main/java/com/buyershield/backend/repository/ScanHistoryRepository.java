package com.buyershield.backend.repository;

import com.buyershield.backend.model.ScanHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Integer> {
    List<ScanHistory> findByTagUid(String tagUid);
}