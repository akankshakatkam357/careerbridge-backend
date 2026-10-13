package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_results")
public class AssessmentResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private User student;
    @Column(nullable = false) private String skill;
    @Column(nullable = false) private int totalQuestions;
    @Column(nullable = false) private int correctAnswers;
    @Column(nullable = false) private double percentage;
    @Column(nullable = false) private LocalDateTime submittedAt;
    public AssessmentResult() {}
    public AssessmentResult(User student, String skill, int totalQuestions, int correctAnswers) {
        this.student=student; this.skill=skill; this.totalQuestions=totalQuestions; this.correctAnswers=correctAnswers;
        this.percentage = totalQuestions == 0 ? 0 : Math.round(correctAnswers * 10000.0 / totalQuestions) / 100.0;
        this.submittedAt=LocalDateTime.now();
    }
    public Long getId(){return id;} public User getStudent(){return student;} public String getSkill(){return skill;}
    public int getTotalQuestions(){return totalQuestions;} public int getCorrectAnswers(){return correctAnswers;}
    public double getPercentage(){return percentage;} public LocalDateTime getSubmittedAt(){return submittedAt;}
}
