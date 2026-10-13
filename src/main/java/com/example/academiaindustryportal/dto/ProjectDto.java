package com.example.academiaindustryportal.dto;

public class ProjectDto {

    private Long id;
    private String title;
    private String description;
    private String domain;
    private String postedByEmail;

    public ProjectDto() {
    }

    public ProjectDto(Long id, String title, String description, String domain, String postedByEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.domain = domain;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPostedByEmail() {
        return postedByEmail;
    }

    public void setPostedByEmail(String postedByEmail) {
        this.postedByEmail = postedByEmail;
    }
}