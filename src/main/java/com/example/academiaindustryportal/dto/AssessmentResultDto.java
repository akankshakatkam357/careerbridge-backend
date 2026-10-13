package com.example.academiaindustryportal.dto;

import com.example.academiaindustryportal.entity.AssessmentResult;
import java.time.LocalDateTime;

public record AssessmentResultDto(Long id, String skill, int totalQuestions, int correctAnswers, double percentage, String level, LocalDateTime submittedAt) {
    public static AssessmentResultDto from(AssessmentResult r) {
        String level = r.getPercentage() >= 80 ? "Strong" : r.getPercentage() >= 50 ? "Developing" : "Needs improvement";
        return new AssessmentResultDto(r.getId(), r.getSkill(), r.getTotalQuestions(), r.getCorrectAnswers(), r.getPercentage(), level, r.getSubmittedAt());
    }
}
