package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {
    List<PortfolioItem> findByStudentEmailOrderByCreatedAtDesc(String email);
}
