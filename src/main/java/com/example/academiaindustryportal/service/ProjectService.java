package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.ProjectDto;
import com.example.academiaindustryportal.entity.Project;
import com.example.academiaindustryportal.entity.Role;
import com.example.academiaindustryportal.entity.User;
import com.example.academiaindustryportal.repository.ProjectRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProjectDto createProject(ProjectDto dto, String userEmail) {
        User user = findUser(userEmail);
        validateProject(dto);
        Project project = new Project(dto.getTitle().trim(), dto.getDescription().trim(), dto.getDomain().trim(), user);
        return mapToDto(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> getMyProjects(String userEmail) {
        User user = findUser(userEmail);
        return projectRepository.findByPostedById(user.getId()).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto updateProject(Long id, ProjectDto dto, String userEmail) {
        Project project = findOwnedProject(id, userEmail);
        validateProject(dto);
        project.setTitle(dto.getTitle().trim());
        project.setDescription(dto.getDescription().trim());
        project.setDomain(dto.getDomain().trim());
        return mapToDto(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id, String userEmail) {
        projectRepository.delete(findOwnedProject(id, userEmail));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Project findOwnedProject(Long id, String email) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found"));
        User user = findUser(email);
        if (!project.getPostedBy().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new org.springframework.security.access.AccessDeniedException("You can only modify projects you created");
        }
        return project;
    }

    private void validateProject(ProjectDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new IllegalArgumentException("Project title is required");
        if (dto.getDescription() == null || dto.getDescription().isBlank()) throw new IllegalArgumentException("Project description is required");
        if (dto.getDomain() == null || dto.getDomain().isBlank()) throw new IllegalArgumentException("Project domain is required");
    }

    private ProjectDto mapToDto(Project p) {
        ProjectDto dto = new ProjectDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setDescription(p.getDescription());
        dto.setDomain(p.getDomain());
        dto.setPostedByEmail(p.getPostedBy().getEmail());
        return dto;
    }
}
