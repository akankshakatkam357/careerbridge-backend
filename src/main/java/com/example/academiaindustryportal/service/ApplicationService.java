package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.ApplicationDto;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final InternshipRepository internshipRepository;
    private final OpportunityRepository opportunityRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            InternshipRepository internshipRepository,
            OpportunityRepository opportunityRepository) {

        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.internshipRepository = internshipRepository;
        this.opportunityRepository = opportunityRepository;
    }

    // Student submits an application
    @Transactional
    public ApplicationDto apply(ApplicationDto dto, String studentEmail) {

        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        Application app = new Application();

        app.setStudent(student);
        app.setCoverLetter(dto.getCoverLetter());

        // Save internship application form details
        app.setDegree(dto.getDegree());
        app.setSkills(dto.getSkills());
        app.setExperience(dto.getExperience());
        app.setProjectDescription(dto.getProjectDescription());
        app.setSuitability(dto.getSuitability());
        app.setResumeLink(dto.getResumeLink());

        // Associate the application with its selected target
        if (dto.getProjectId() != null) {

            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Project not found"));

            app.setProject(project);

        } else if (dto.getInternshipId() != null) {

            Internship internship = internshipRepository
                    .findById(dto.getInternshipId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Internship not found"));

            app.setInternship(internship);

        } else if (dto.getOpportunityId() != null) {

            Opportunity opportunity = opportunityRepository
                    .findById(dto.getOpportunityId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Opportunity not found"));

            // Prevent duplicate applications to the same opportunity
            if (applicationRepository.existsByStudentIdAndOpportunityId(
                    student.getId(), opportunity.getId())) {

                throw new IllegalArgumentException(
                        "You have already applied to this opportunity");
            }

            app.setOpportunity(opportunity);

        } else {
            throw new IllegalArgumentException(
                    "Application must target a Project, Internship, or Opportunity");
        }

        Application saved = applicationRepository.save(app);

        return mapToDto(saved);
    }

    // Get all applications submitted by a student
    @Transactional(readOnly = true)
    public List<ApplicationDto> getStudentApplications(String studentEmail) {

        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        return applicationRepository.findByStudentId(student.getId())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Recruiter views applicants for their opportunity
    @Transactional(readOnly = true)
    public List<ApplicationDto> getOpportunityApplications(
            Long opportunityId, String actorEmail) {

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Opportunity opportunity = opportunityRepository
                .findById(opportunityId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Opportunity not found"));

        // Industry users can only view applicants for their own postings
        if (actor.getRole() == Role.ROLE_INDUSTRY
                && !isSameUser(opportunity.getPostedBy(), actor)) {

            throw new IllegalArgumentException(
                    "You can only view applicants of your own opportunities");
        }

        return applicationRepository.findByOpportunityId(opportunityId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Approve or reject an application
    @Transactional
    public ApplicationDto updateStatus(
            Long applicationId,
            ApplicationStatus status,
            String actorEmail) {

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Application not found"));

        // Industry users can only update applications for their own postings
        if (actor.getRole() == Role.ROLE_INDUSTRY
                && !isSameUser(findOwner(app), actor)) {

            throw new IllegalArgumentException(
                    "You can only update applications to your own postings");
        }

        app.setStatus(status);

        Application updated = applicationRepository.save(app);

        return mapToDto(updated);
    }

    // Find the owner of the application target
    private User findOwner(Application app) {

        if (app.getOpportunity() != null) {
            return app.getOpportunity().getPostedBy();
        }

        if (app.getInternship() != null) {
            return app.getInternship().getPostedBy();
        }

        if (app.getProject() != null) {
            return app.getProject().getPostedBy();
        }

        return null;
    }

    private boolean isSameUser(User owner, User actor) {

        return owner != null
                && owner.getId() != null
                && owner.getId().equals(actor.getId());
    }

    // Convert Application entity into ApplicationDto
    private ApplicationDto mapToDto(Application app) {

        ApplicationDto dto = new ApplicationDto();

        dto.setId(app.getId());

        if (app.getStudent() != null) {
            dto.setStudentId(app.getStudent().getId());
            dto.setStudentEmail(app.getStudent().getEmail());
            dto.setStudentName(app.getStudent().getFullName());
        }

        if (app.getProject() != null) {
            dto.setProjectId(app.getProject().getId());
        }

        if (app.getInternship() != null) {
            dto.setInternshipId(app.getInternship().getId());
        }

        if (app.getOpportunity() != null) {
            dto.setOpportunityId(app.getOpportunity().getId());
            dto.setOpportunityTitle(app.getOpportunity().getTitle());
        }

        dto.setCoverLetter(app.getCoverLetter());
        dto.setStatus(app.getStatus());
        dto.setAppliedAt(app.getAppliedAt());

        // Return all application form details to the frontend
        dto.setDegree(app.getDegree());
        dto.setSkills(app.getSkills());
        dto.setExperience(app.getExperience());
        dto.setProjectDescription(app.getProjectDescription());
        dto.setSuitability(app.getSuitability());
        dto.setResumeLink(app.getResumeLink());

        return dto;
    }
}