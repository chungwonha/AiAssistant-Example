package com.chung.cool.aiassistant.rmf.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Project findByName(String name);
    Project findByKey(String key);
}
