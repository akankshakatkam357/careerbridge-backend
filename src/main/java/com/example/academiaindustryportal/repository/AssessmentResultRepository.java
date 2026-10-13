package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.AssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
    List<AssessmentResult> findByStudentEmailOrderBySubmittedAtDesc(String email);
}
