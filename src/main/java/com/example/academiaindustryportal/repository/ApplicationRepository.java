package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByProjectId(Long projectId);
    List<Application> findByInternshipId(Long internshipId);
    List<Application> findByOpportunityId(Long opportunityId);
    boolean existsByStudentIdAndOpportunityId(Long studentId, Long opportunityId);
}
