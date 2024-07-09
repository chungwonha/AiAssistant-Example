package com.chung.cool.aiassistant.rmf.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowLocationRepository extends JpaRepository<FlowLocation, Long> {
    List<FlowLocation> findByTextRangeStartLineLessThan(int line);
}