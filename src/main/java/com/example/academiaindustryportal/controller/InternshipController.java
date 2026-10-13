package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.InternshipDto;
import com.example.academiaindustryportal.service.InternshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    private final InternshipService internshipService;

    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<InternshipDto>> createInternship(@RequestBody InternshipDto dto, Authentication authentication) {
        InternshipDto created = internshipService.createInternship(dto, authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Internship created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InternshipDto>>> getAllInternships() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Internships retrieved", internshipService.getAllInternships()));
    }
}
