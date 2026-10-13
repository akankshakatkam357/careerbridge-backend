package com.example.academiaindustryportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "opportunity_skills")
public class OpportunitySkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id", nullable = false)
    private Opportunity opportunity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer requiredProficiency;

    public OpportunitySkill() {
    }

    public OpportunitySkill(Opportunity opportunity, String name, Integer requiredProficiency) {
        this.opportunity = opportunity;
        this.name = name;
        this.requiredProficiency = requiredProficiency;
    }

    public Long getId() { return id; }
    public Opportunity getOpportunity() { return opportunity; }
    public void setOpportunity(Opportunity opportunity) { this.opportunity = opportunity; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getRequiredProficiency() { return requiredProficiency; }
    public void setRequiredProficiency(Integer requiredProficiency) { this.requiredProficiency = requiredProficiency; }
}
