package com.chung.cool.aiassistant.rmf.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    List<Issue> findBySeverity(String severity);
    List<Issue> findByComponent(String component);
    List<Issue> findByTextRangeStartLineBetween(int startLine, int endLine);
    List<Issue> findAllIssuesByProject(String project);

}
