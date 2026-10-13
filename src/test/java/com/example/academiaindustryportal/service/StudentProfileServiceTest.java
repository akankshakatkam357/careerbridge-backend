package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.StudentProfileDto;
import com.example.academiaindustryportal.entity.StudentProfile;
import com.example.academiaindustryportal.entity.User;
import com.example.academiaindustryportal.repository.StudentProfileRepository;
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
class StudentProfileServiceTest {

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudentProfileService studentProfileService;

    @Test
    void createProfileCreatesProfileForStudent() {
        User student = new User("student@example.com", "password", "Student", com.example.academiaindustryportal.entity.Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));
        when(studentProfileRepository.findByStudentId(1L)).thenReturn(Optional.empty());
        when(studentProfileRepository.save(any(StudentProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentProfileDto result = studentProfileService.createProfile("student@example.com", "Computer Science", "B.Tech");

        assertEquals("Computer Science", result.getDepartment());
        assertEquals("B.Tech", result.getProgram());
        assertEquals(1L, result.getStudentId());
    }

    @Test
    void getProfileReturnsExistingProfile() {
        User student = new User("student@example.com", "password", "Student", com.example.academiaindustryportal.entity.Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);
        StudentProfile profile = new StudentProfile(student, "Computer Science", "B.Tech");
        ReflectionTestUtils.setField(profile, "id", 10L);

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));
        when(studentProfileRepository.findByStudentId(1L)).thenReturn(Optional.of(profile));

        StudentProfileDto result = studentProfileService.getProfile("student@example.com");

        assertEquals(10L, result.getId());
        assertEquals("Computer Science", result.getDepartment());
    }

    @Test
    void createProfileRejectsNonStudentUser() {
        User faculty = new User("faculty@example.com", "password", "Faculty", com.example.academiaindustryportal.entity.Role.ROLE_FACULTY);
        ReflectionTestUtils.setField(faculty, "id", 2L);

        when(userRepository.findByEmail("faculty@example.com")).thenReturn(Optional.of(faculty));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentProfileService.createProfile("faculty@example.com", "Computer Science", "B.Tech")
        );

        assertEquals("Only students can create a profile", exception.getMessage());
    }

    @Test
    void getProfileThrowsForMissingProfile() {
        User student = new User("student@example.com", "password", "Student", com.example.academiaindustryportal.entity.Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));
        when(studentProfileRepository.findByStudentId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentProfileService.getProfile("student@example.com")
        );

        assertEquals("Student profile not found", exception.getMessage());
    }

    @Test
    void getStudentSkillsReturnsSkillsForProfile() {
        User student = new User("student@example.com", "password", "Student", com.example.academiaindustryportal.entity.Role.ROLE_STUDENT);
        ReflectionTestUtils.setField(student, "id", 1L);
        StudentProfile profile = new StudentProfile(student, "Computer Science", "B.Tech");
        profile.getSkills().add(new com.example.academiaindustryportal.entity.Skill(profile, "Java", 4));

        when(userRepository.findByEmail("student@example.com")).thenReturn(Optional.of(student));
        when(studentProfileRepository.findByStudentId(1L)).thenReturn(Optional.of(profile));

        List<com.example.academiaindustryportal.dto.SkillDto> result = studentProfileService.getSkills("student@example.com");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals(4, result.get(0).getProficiency());
    }
}
