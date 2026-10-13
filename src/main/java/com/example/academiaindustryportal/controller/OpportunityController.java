package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.OpportunityDto;
import com.example.academiaindustryportal.service.OpportunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_INDUSTRY')")
    public ResponseEntity<ApiResponse<OpportunityDto>> createOpportunity(
            @RequestBody OpportunityDto opportunity,
            Authentication authentication) {
        OpportunityDto created = opportunityService.createOpportunity(authentication.getName(), opportunity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Opportunity created", created));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAuthority('ROLE_INDUSTRY')")
    public ResponseEntity<ApiResponse<List<OpportunityDto>>> getMyOpportunities(Authentication authentication) {
        List<OpportunityDto> opportunities = opportunityService.getMyOpportunities(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Opportunities retrieved", opportunities));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OpportunityDto>>> getOpportunities(Authentication authentication) {
        List<OpportunityDto> opportunities = opportunityService.getOpportunities(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Opportunities retrieved", opportunities));
    }
}
