package com.chung.cool.aiassistant.rmf.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanResultRepository extends JpaRepository<ScanResult, Long> {
}
