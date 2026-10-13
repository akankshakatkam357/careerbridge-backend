package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.ApplicationDto;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.*;
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
class ApplicationStatusAuthorizationTest {

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

    private User user(String email, Role role, long id) {
        User user = new User(email, "password", "Name", role);
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    private Application applicationOwnedBy(User owner) {
        User student = user("student@example.com", Role.ROLE_STUDENT, 1L);
        Opportunity opportunity = new Opportunity("Dev", "Build", "Tech", "Example", "Remote", OpportunityType.JOB, owner);
        Application application = new Application();
        application.setStudent(student);
        application.setOpportunity(opportunity);
        return application;
    }

    @Test
    void industryUserCannotUpdateApplicationOfAnotherCompany() {
        User owner = user("owner@example.com", Role.ROLE_INDUSTRY, 2L);
        User other = user("other@example.com", Role.ROLE_INDUSTRY, 3L);

        when(userRepository.findByEmail("other@example.com")).thenReturn(Optional.of(other));
        when(applicationRepository.findById(5L)).thenReturn(Optional.of(applicationOwnedBy(owner)));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> applicationService.updateStatus(5L, ApplicationStatus.APPROVED, "other@example.com")
        );

        assertEquals("You can only update applications to your own postings", exception.getMessage());
    }

    @Test
    void industryOwnerCanApproveApplication() {
        User owner = user("owner@example.com", Role.ROLE_INDUSTRY, 2L);

        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(applicationRepository.findById(5L)).thenReturn(Optional.of(applicationOwnedBy(owner)));
        when(applicationRepository.save(any(Application.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ApplicationDto result = applicationService.updateStatus(5L, ApplicationStatus.APPROVED, "owner@example.com");

        assertEquals(ApplicationStatus.APPROVED, result.getStatus());
    }
}
