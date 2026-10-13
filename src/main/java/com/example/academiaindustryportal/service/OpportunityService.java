package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.OpportunityDto;
import com.example.academiaindustryportal.dto.OpportunitySkillDto;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.OpportunityRepository;
import com.example.academiaindustryportal.repository.StudentProfileRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public OpportunityService(OpportunityRepository opportunityRepository,
                              UserRepository userRepository,
                              StudentProfileRepository studentProfileRepository) {
        this.opportunityRepository = opportunityRepository;
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    @Transactional
    public OpportunityDto createOpportunity(String userEmail, OpportunityDto dto) {
        User poster = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (poster.getRole() != Role.ROLE_INDUSTRY) {
            throw new IllegalArgumentException("Only industry users can create opportunities");
        }

        validateOpportunity(dto);

        Opportunity opportunity = new Opportunity(
                dto.getTitle(),
                dto.getDescription(),
                dto.getDomain(),
                dto.getCompanyName(),
                dto.getLocation(),
                dto.getType(),
                poster
        );

        if (dto.getRequiredSkills() != null) {
            for (OpportunitySkillDto skillDto : dto.getRequiredSkills()) {
                validateSkill(skillDto);
                opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, skillDto.getName(), skillDto.getRequiredProficiency()));
            }
        }

        Opportunity saved = opportunityRepository.save(opportunity);
        return mapToDto(saved, null);
    }

    @Transactional(readOnly = true)
    public List<OpportunityDto> getMyOpportunities(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return opportunityRepository.findByPostedByIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(opportunity -> mapToDto(opportunity, null))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OpportunityDto> getOpportunities(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        StudentProfile studentProfile = user.getRole() == Role.ROLE_STUDENT
                ? studentProfileRepository.findByStudentId(user.getId()).orElse(null)
                : null;

        return opportunityRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(opportunity -> mapToDto(opportunity, studentProfile))
                .collect(Collectors.toList());
    }

    public int calculateMatch(StudentProfile profile, Opportunity opportunity) {
        if (profile == null || opportunity.getRequiredSkills().isEmpty()) {
            return 0;
        }

        Map<String, Integer> studentSkills = profile.getSkills().stream()
                .collect(Collectors.toMap(skill -> skill.getName().trim().toLowerCase(), Skill::getProficiency, Math::max));

        int totalCoverage = 0;
        for (OpportunitySkill required : opportunity.getRequiredSkills()) {
            Integer studentProficiency = studentSkills.get(required.getName().trim().toLowerCase());
            if (studentProficiency == null) {
                continue;
            }
            totalCoverage += Math.min(studentProficiency, required.getRequiredProficiency());
        }

        int totalRequired = opportunity.getRequiredSkills().stream()
                .mapToInt(OpportunitySkill::getRequiredProficiency)
                .sum();

        if (totalRequired == 0) {
            return 0;
        }

        return Math.round((totalCoverage / (float) totalRequired) * 100);
    }

    private void validateOpportunity(OpportunityDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Opportunity title is required");
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Opportunity description is required");
        }
        if (dto.getDomain() == null || dto.getDomain().isBlank()) {
            throw new IllegalArgumentException("Opportunity domain is required");
        }
        if (dto.getCompanyName() == null || dto.getCompanyName().isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (dto.getLocation() == null || dto.getLocation().isBlank()) {
            throw new IllegalArgumentException("Location is required");
        }
        if (dto.getType() == null) {
            throw new IllegalArgumentException("Opportunity type is required");
        }
    }

    private void validateSkill(OpportunitySkillDto skillDto) {
        if (skillDto == null || skillDto.getName() == null || skillDto.getName().isBlank()) {
            throw new IllegalArgumentException("Required skill name is required");
        }
        if (skillDto.getRequiredProficiency() == null || skillDto.getRequiredProficiency() < 1 || skillDto.getRequiredProficiency() > 5) {
            throw new IllegalArgumentException("Required skill proficiency must be between 1 and 5");
        }
    }

    private OpportunityDto mapToDto(Opportunity opportunity, StudentProfile profile) {
        OpportunityDto dto = new OpportunityDto(
                opportunity.getId(),
                opportunity.getTitle(),
                opportunity.getDescription(),
                opportunity.getDomain(),
                opportunity.getCompanyName(),
                opportunity.getLocation(),
                opportunity.getType(),
                opportunity.getRequiredSkills().stream()
                        .map(skill -> new OpportunitySkillDto(skill.getName(), skill.getRequiredProficiency()))
                        .collect(Collectors.toList())
        );
        dto.setPostedById(opportunity.getPostedBy().getId());
        dto.setPostedByName(opportunity.getPostedBy().getFullName());
        dto.setCreatedAt(opportunity.getCreatedAt());
        dto.setMatchPercentage(profile == null ? null : calculateMatch(profile, opportunity));
        return dto;
    }
}
