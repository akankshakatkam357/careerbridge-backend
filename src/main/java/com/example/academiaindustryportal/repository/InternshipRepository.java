package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByPostedById(Long userId);
}