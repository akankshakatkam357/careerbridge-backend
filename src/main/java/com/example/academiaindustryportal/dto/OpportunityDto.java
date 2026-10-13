package com.example.academiaindustryportal.dto;

import com.example.academiaindustryportal.entity.OpportunityType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpportunityDto {
    private Long id;
    private String title;
    private String description;
    private String domain;
    private String companyName;
    private String location;
    private OpportunityType type;
    private Long postedById;
    private String postedByName;
    private LocalDateTime createdAt;
    private Integer matchPercentage;
    private List<OpportunitySkillDto> requiredSkills = new ArrayList<>();

    public OpportunityDto() {
    }

    public OpportunityDto(Long id, String title, String description, String domain, String companyName,
                          String location, OpportunityType type, List<OpportunitySkillDto> requiredSkills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.domain = domain;
        this.companyName = companyName;
        this.location = location;
        this.type = type;
        this.requiredSkills = requiredSkills;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public OpportunityType getType() { return type; }
    public void setType(OpportunityType type) { this.type = type; }
    public Long getPostedById() { return postedById; }
    public void setPostedById(Long postedById) { this.postedById = postedById; }
    public String getPostedByName() { return postedByName; }
    public void setPostedByName(String postedByName) { this.postedByName = postedByName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Integer getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; }
    public List<OpportunitySkillDto> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<OpportunitySkillDto> requiredSkills) { this.requiredSkills = requiredSkills; }
}
