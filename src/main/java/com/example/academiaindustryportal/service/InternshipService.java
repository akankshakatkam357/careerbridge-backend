package com.example.academiaindustryportal.service;

import com.example.academiaindustryportal.dto.InternshipDto;
import com.example.academiaindustryportal.entity.Internship;
import com.example.academiaindustryportal.entity.User;
import com.example.academiaindustryportal.repository.InternshipRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    public InternshipService(InternshipRepository internshipRepository, UserRepository userRepository) {
        this.internshipRepository = internshipRepository;
        this.userRepository = userRepository;
    }

    public InternshipDto createInternship(InternshipDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Internship internship = new Internship(
                dto.getTitle(), dto.getCompanyName(), dto.getDescription(),
                dto.getStipend(), dto.getDuration(), user
        );

        Internship saved = internshipRepository.save(internship);
        return mapToDto(saved);
    }

    public List<InternshipDto> getAllInternships() {
        return internshipRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private InternshipDto mapToDto(Internship i) {
        InternshipDto dto = new InternshipDto();
        dto.setId(i.getId());
        dto.setTitle(i.getTitle());
        dto.setCompanyName(i.getCompanyName());
        dto.setDescription(i.getDescription());
        dto.setStipend(i.getStipend());
        dto.setDuration(i.getDuration());
        dto.setPostedByEmail(i.getPostedBy().getEmail());
        return dto;
    }
}