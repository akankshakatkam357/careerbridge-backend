package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.SkillDto;
import com.example.academiaindustryportal.dto.StudentProfileDto;
import com.example.academiaindustryportal.entity.Skill;
import com.example.academiaindustryportal.entity.StudentProfile;
import com.example.academiaindustryportal.entity.User;
import com.example.academiaindustryportal.repository.StudentProfileRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository, UserRepository userRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StudentProfileDto createProfile(String userEmail, String department, String program) {
        User student = findStudent(userEmail);

        if (studentProfileRepository.findByStudentId(student.getId()).isPresent()) {
            throw new IllegalArgumentException("Student profile already exists");
        }

        StudentProfile profile = new StudentProfile(student, department, program);
        StudentProfile saved = studentProfileRepository.save(profile);
        return mapToProfileDto(saved);
    }

    @Transactional(readOnly = true)
    public StudentProfileDto getProfile(String userEmail) {
        User student = findStudent(userEmail);
        StudentProfile profile = studentProfileRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found"));
        return mapToProfileDto(profile);
    }

    @Transactional
    public SkillDto addSkill(String userEmail, String name, int proficiency) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Skill name is required");
        }
        if (proficiency < 1 || proficiency > 5) {
            throw new IllegalArgumentException("Proficiency must be between 1 and 5");
        }

        User student = findStudent(userEmail);
        StudentProfile profile = studentProfileRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found"));

        Skill skill = new Skill(profile, name.trim(), proficiency);
        profile.getSkills().add(skill);

        return mapToSkillDto(skill);
    }

    @Transactional(readOnly = true)
    public List<SkillDto> getSkills(String userEmail) {
        User student = findStudent(userEmail);
        StudentProfile profile = studentProfileRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new IllegalArgumentException("Student profile not found"));

        return profile.getSkills().stream()
                .map(this::mapToSkillDto)
                .collect(Collectors.toList());
    }

    private User findStudent(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole() != com.example.academiaindustryportal.entity.Role.ROLE_STUDENT) {
            throw new IllegalArgumentException("Only students can create a profile");
        }

        return user;
    }

    private StudentProfileDto mapToProfileDto(StudentProfile profile) {
        return new StudentProfileDto(
                profile.getId(),
                profile.getStudent().getId(),
                profile.getDepartment(),
                profile.getProgram()
        );
    }

    private SkillDto mapToSkillDto(Skill skill) {
        return new SkillDto(skill.getId(), skill.getName(), skill.getProficiency());
    }
}
