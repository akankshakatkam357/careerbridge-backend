package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.ApplicationDto;
import com.example.academiaindustryportal.entity.ApplicationStatus;
import com.example.academiaindustryportal.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<ApplicationDto>> apply(@RequestBody ApplicationDto dto, Authentication authentication) {
        ApplicationDto created = applicationService.apply(dto, authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Application submitted successfully", created));
    }

    @GetMapping("/my-applications")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getMyApplications(Authentication authentication) {
        List<ApplicationDto> apps = applicationService.getStudentApplications(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Applications retrieved", apps));
    }

    @GetMapping("/opportunity/{opportunityId}")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getOpportunityApplications(
            @PathVariable Long opportunityId, Authentication authentication) {
        List<ApplicationDto> apps = applicationService.getOpportunityApplications(opportunityId, authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Applications retrieved", apps));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<ApplicationDto>> updateStatus(
            @PathVariable Long id, @RequestParam ApplicationStatus status, Authentication authentication) {
        ApplicationDto updated = applicationService.updateStatus(id, status, authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Status updated successfully", updated));
    }
}