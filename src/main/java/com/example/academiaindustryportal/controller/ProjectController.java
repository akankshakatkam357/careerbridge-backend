package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.ProjectDto;
import com.example.academiaindustryportal.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) { this.projectService = projectService; }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(@RequestBody ProjectDto dto, Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Project created successfully", projectService.createProject(dto, auth.getName())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDto>>> getAllProjects() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Projects retrieved", projectService.getAllProjects()));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<ProjectDto>>> getMyProjects(Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Your projects retrieved", projectService.getMyProjects(auth.getName())));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<ProjectDto>> updateProject(@PathVariable Long id, @RequestBody ProjectDto dto, Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Project updated successfully", projectService.updateProject(id, dto, auth.getName())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY', 'ROLE_INDUSTRY', 'ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id, Authentication auth) {
        projectService.deleteProject(id, auth.getName());
        return ResponseEntity.ok(new ApiResponse<>(true, "Project deleted successfully", null));
    }
}
