package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "internships")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private Double stipend;
    private String duration; // e.g., "3 Months"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by_id", nullable = false)
    private User postedBy;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Internship() {}

    public Internship(String title, String companyName, String description, Double stipend, String duration, User postedBy) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.stipend = stipend;
        this.duration = duration;
        this.postedBy = postedBy;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getStipend() { return stipend; }
    public void setStipend(Double stipend) { this.stipend = stipend; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}