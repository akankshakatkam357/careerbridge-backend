package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "student_portfolio_items")
public class PortfolioItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    @Column(nullable = false, length = 30)
    private String type;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(length = 1200)
    private String description;
    @Column(length = 500)
    private String url;
    @Column(name = "completion_date", length = 10)
    private String date;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @PrePersist public void onCreate() { if (createdAt == null) createdAt = Instant.now(); }
    public PortfolioItem() {}
    public Long getId() { return id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public Instant getCreatedAt() { return createdAt; }
}
