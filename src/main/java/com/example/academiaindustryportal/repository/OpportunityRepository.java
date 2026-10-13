package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findAllByOrderByCreatedAtDesc();
    List<Opportunity> findByPostedByIdOrderByCreatedAtDesc(Long userId);
}
