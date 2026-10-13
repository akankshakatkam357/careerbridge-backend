package com.example.academiaindustryportal.dto;

public class InternshipDto {

    private Long id;
    private String title;
    private String companyName;
    private String description;
    private Double stipend;
    private String duration;
    private String postedByEmail;

    public InternshipDto() {
    }

    public InternshipDto(Long id, String title, String companyName, String description, Double stipend, String duration, String postedByEmail) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.stipend = stipend;
        this.duration = duration;
        this.postedByEmail = postedByEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStipend() {
        return stipend;
    }

    public void setStipend(Double stipend) {
        this.stipend = stipend;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPostedByEmail() {
        return postedByEmail;
    }

    public void setPostedByEmail(String postedByEmail) {
        this.postedByEmail = postedByEmail;
    }
}