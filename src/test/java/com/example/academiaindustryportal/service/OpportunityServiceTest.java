package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.OpportunityDto;
import com.example.academiaindustryportal.dto.OpportunitySkillDto;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.OpportunityRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpportunityServiceTest {

    @Mock
    private OpportunityRepository opportunityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OpportunityService opportunityService;

    @Test
    void createOpportunityCreatesOpportunityForIndustryUser() {
        User industry = new User("industry@example.com", "password", "Industry", Role.ROLE_INDUSTRY);
        ReflectionTestUtils.setField(industry, "id", 3L);

        when(userRepository.findByEmail("industry@example.com")).thenReturn(Optional.of(industry));
        when(opportunityRepository.save(any(Opportunity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OpportunityDto result = opportunityService.createOpportunity("industry@example.com", new OpportunityDto(
                null, "Software Engineer", "Build products", "Technology",
                "Example Ltd", "Remote", OpportunityType.JOB,
                List.of(new OpportunitySkillDto("Java", 4), new OpportunitySkillDto("Spring", 3))
        ));

        assertEquals("Software Engineer", result.getTitle());
        assertEquals(2, result.getRequiredSkills().size());
        assertEquals(3L, result.getPostedById());
    }

    @Test
    void createOpportunityRejectsNonIndustryUser() {
        User student = new User("student@example.com", "password", "Student", Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> opportunityService.createOpportunity("student@example.com", new OpportunityDto(
                        null, "Software Engineer", "Build products", "Technology",
                        "Example Ltd", "Remote", OpportunityType.JOB,
                        List.of(new OpportunitySkillDto("Java", 4))
                ))
        );

        assertEquals("Only industry users can create opportunities", exception.getMessage());
    }

    @Test
    void calculateMatchUsesAverageRequiredSkillCoverage() {
        User student = new User("student@example.com", "password", "Student", Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);
        StudentProfile profile = new StudentProfile(student, "Computer Science", "B.Tech");
        profile.getSkills().add(new Skill(profile, "Java", 4));
        profile.getSkills().add(new Skill(profile, "Spring", 2));
        profile.getSkills().add(new Skill(profile, "Testing", 5));

        Opportunity opportunity = new Opportunity("Software Engineer", "Build products", "Technology",
                "Example Ltd", "Remote", OpportunityType.JOB, student);
        opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, "Java", 4));
        opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, "Spring", 4));
        opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, "Testing", 4));

        int match = opportunityService.calculateMatch(profile, opportunity);

        assertEquals(83, match);
    }
}
