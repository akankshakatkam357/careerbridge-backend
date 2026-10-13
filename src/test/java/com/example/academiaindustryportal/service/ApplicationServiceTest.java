package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.ApplicationDto;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.ApplicationRepository;
import com.example.academiaindustryportal.repository.InternshipRepository;
import com.example.academiaindustryportal.repository.OpportunityRepository;
import com.example.academiaindustryportal.repository.ProjectRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private InternshipRepository internshipRepository;

    @Mock
    private OpportunityRepository opportunityRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void applyRejectsDuplicateOpportunityApplication() {
        User student = new User("student@example.com", "password", "Student", Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);
        User industry = new User("industry@example.com", "password", "Industry", Role.ROLE_INDUSTRY);
        ReflectionTestUtils.setField(industry, "id", 2L);
        Opportunity opportunity = new Opportunity("Developer", "Build", "Technology", "Example", "Remote", OpportunityType.JOB, industry);
        ReflectionTestUtils.setField(opportunity, "id", 10L);

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));
        when(opportunityRepository.findById(10L)).thenReturn(Optional.of(opportunity));
        when(applicationRepository.existsByStudentIdAndOpportunityId(1L, 10L)).thenReturn(true);

        ApplicationDto dto = new ApplicationDto();
        dto.setOpportunityId(10L);
        dto.setCoverLetter("Interested");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> applicationService.apply(dto, "student@example.com")
        );

        assertEquals("You have already applied to this opportunity", exception.getMessage());
    }
}
