package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.SkillDto;
import com.example.academiaindustryportal.dto.StudentProfileDto;
import com.example.academiaindustryportal.service.StudentProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<StudentProfileDto>> createProfile(
            @RequestParam String department,
            @RequestParam String program,
            Authentication authentication) {
        StudentProfileDto profile = studentProfileService.createProfile(authentication.getName(), department, program);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student profile created", profile));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<StudentProfileDto>> getProfile(Authentication authentication) {
        StudentProfileDto profile = studentProfileService.getProfile(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Student profile retrieved", profile));
    }

    @PostMapping("/skills")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<SkillDto>> addSkill(
            @RequestParam String name,
            @RequestParam int proficiency,
            Authentication authentication) {
        SkillDto skill = studentProfileService.addSkill(authentication.getName(), name, proficiency);
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill added", skill));
    }

    @GetMapping("/skills")
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<List<SkillDto>>> getSkills(Authentication authentication) {
        List<SkillDto> skills = studentProfileService.getSkills(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Skills retrieved", skills));
    }
}
