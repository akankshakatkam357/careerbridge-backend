package com.example.academiaindustryportal.dto;

public class SkillDto {
    private Long id;
    private String name;
    private int proficiency;

    public SkillDto() {
    }

    public SkillDto(Long id, String name, int proficiency) {
        this.id = id;
        this.name = name;
        this.proficiency = proficiency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }
}
