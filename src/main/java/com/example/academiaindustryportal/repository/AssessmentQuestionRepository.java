package com.example.academiaindustryportal.repository;

import com.example.academiaindustryportal.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long> {
    List<AssessmentQuestion> findBySkillIgnoreCase(String skill);
    boolean existsBySkillIgnoreCase(String skill);
}
