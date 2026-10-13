package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.assessment.AssessmentService;
import com.example.academiaindustryportal.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@PreAuthorize("hasRole('STUDENT')")
public class AssessmentController {
    private final AssessmentService assessmentService;
    public AssessmentController(AssessmentService assessmentService) { this.assessmentService=assessmentService; }

    @GetMapping("/questions")
    public ResponseEntity<ApiResponse<List<AssessmentQuestionDto>>> questions(@RequestParam String skill) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Assessment questions loaded", assessmentService.getQuestions(skill)));
    }
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<AssessmentResultDto>> submit(@Valid @RequestBody AssessmentSubmissionDto submission, Authentication authentication) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Assessment submitted successfully", assessmentService.submit(authentication.getName(), submission)));
    }
    @GetMapping("/my-results")
    public ResponseEntity<ApiResponse<List<AssessmentResultDto>>> myResults(Authentication authentication) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Assessment results loaded", assessmentService.getMyResults(authentication.getName())));
    }
}
