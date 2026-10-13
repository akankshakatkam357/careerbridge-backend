package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assessment_questions")
public class AssessmentQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String skill;
    @Column(nullable = false, length = 1000) private String questionText;
    @Column(nullable = false) private String optionA;
    @Column(nullable = false) private String optionB;
    @Column(nullable = false) private String optionC;
    @Column(nullable = false) private String optionD;
    @Column(nullable = false, length = 1) private String correctOption;
    @Column(nullable = false) private String difficulty;
    public AssessmentQuestion() {}
    public AssessmentQuestion(String skill, String questionText, String optionA, String optionB, String optionC, String optionD, String correctOption, String difficulty) {
        this.skill=skill; this.questionText=questionText; this.optionA=optionA; this.optionB=optionB; this.optionC=optionC; this.optionD=optionD; this.correctOption=correctOption; this.difficulty=difficulty;
    }
    public Long getId(){return id;} public String getSkill(){return skill;} public String getQuestionText(){return questionText;}
    public String getOptionA(){return optionA;} public String getOptionB(){return optionB;} public String getOptionC(){return optionC;} public String getOptionD(){return optionD;}
    public String getCorrectOption(){return correctOption;} public String getDifficulty(){return difficulty;}
}
