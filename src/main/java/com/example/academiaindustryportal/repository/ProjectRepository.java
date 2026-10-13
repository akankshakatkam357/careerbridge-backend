package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByPostedById(Long userId);
}