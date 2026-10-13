package com.example.academiaindustryportal.dto;

import com.example.academiaindustryportal.entity.PortfolioItem;

public class PortfolioItemDto {
    private Long id;
    private String type;
    private String title;
    private String description;
    private String url;
    private String date;
    public PortfolioItemDto() {}
    public static PortfolioItemDto from(PortfolioItem item) {
        PortfolioItemDto dto = new PortfolioItemDto();
        dto.id = item.getId(); dto.type = item.getType(); dto.title = item.getTitle();
        dto.description = item.getDescription(); dto.url = item.getUrl(); dto.date = item.getDate();
        return dto;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
}
