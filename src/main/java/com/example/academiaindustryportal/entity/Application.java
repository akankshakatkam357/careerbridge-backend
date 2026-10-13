package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id")
    private Internship internship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column(length = 100)
private String degree;

@Column(columnDefinition = "TEXT")
private String skills;

@Column(length = 100)
private String experience;

@Column(columnDefinition = "TEXT")
private String projectDescription;

@Column(columnDefinition = "TEXT")
private String suitability;

@Column(length = 1000)
private String resumeLink;

    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
    }

    public Application() {}

    public Long getId() { return id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public Internship getInternship() { return internship; }
    public void setInternship(Internship internship) { this.internship = internship; }
    public Opportunity getOpportunity() { return opportunity; }
    public void setOpportunity(Opportunity opportunity) { this.opportunity = opportunity; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }
    public LocalDateTime getAppliedAt() { return appliedAt; }

    public String getDegree() { return degree; }
public void setDegree(String degree) { this.degree = degree; }

public String getSkills() { return skills; }
public void setSkills(String skills) { this.skills = skills; }

public String getExperience() { return experience; }
public void setExperience(String experience) { this.experience = experience; }

public String getProjectDescription() { return projectDescription; }
public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
}

public String getSuitability() { return suitability; }
public void setSuitability(String suitability) {
    this.suitability = suitability;
}

public String getResumeLink() { return resumeLink; }
public void setResumeLink(String resumeLink) {
    this.resumeLink = resumeLink;
}




}

