package com.example.academiaindustryportal.dto;

public class OpportunitySkillDto {
    private Long id;
    private String name;
    private Integer requiredProficiency;

    public OpportunitySkillDto() {
    }

    public OpportunitySkillDto(String name, Integer requiredProficiency) {
        this.name = name;
        this.requiredProficiency = requiredProficiency;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getRequiredProficiency() { return requiredProficiency; }
    public void setRequiredProficiency(Integer requiredProficiency) { this.requiredProficiency = requiredProficiency; }
}
