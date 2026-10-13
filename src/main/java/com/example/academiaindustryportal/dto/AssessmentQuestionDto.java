package com.example.academiaindustryportal.dto;

import com.example.academiaindustryportal.entity.AssessmentQuestion;

public record AssessmentQuestionDto(Long id, String skill, String questionText, String optionA, String optionB, String optionC, String optionD, String difficulty) {
    public static AssessmentQuestionDto from(AssessmentQuestion q) {
        return new AssessmentQuestionDto(q.getId(), q.getSkill(), q.getQuestionText(), q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD(), q.getDifficulty());
    }
}
