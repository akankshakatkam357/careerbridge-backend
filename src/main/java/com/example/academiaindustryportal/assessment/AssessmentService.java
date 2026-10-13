package com.example.academiaindustryportal.assessment;

import com.example.academiaindustryportal.dto.*;
import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AssessmentService {
    private final AssessmentQuestionRepository questionRepository;
    private final AssessmentResultRepository resultRepository;
    private final UserRepository userRepository;
    public AssessmentService(AssessmentQuestionRepository questionRepository, AssessmentResultRepository resultRepository, UserRepository userRepository) {
        this.questionRepository=questionRepository; this.resultRepository=resultRepository; this.userRepository=userRepository;
    }
    @Transactional(readOnly = true)
    public List<AssessmentQuestionDto> getQuestions(String skill) {
        if (skill == null || skill.isBlank()) throw new IllegalArgumentException("Please provide a skill, for example Java");
        List<AssessmentQuestion> questions=questionRepository.findBySkillIgnoreCase(skill.trim());
        if (questions.isEmpty()) throw new NoSuchElementException("No assessment questions are available for: " + skill);
        return questions.stream().map(AssessmentQuestionDto::from).toList();
    }
    @Transactional
    public AssessmentResultDto submit(String email, AssessmentSubmissionDto submission) {
        String skill=submission.skill().trim();
        List<AssessmentQuestion> questions=questionRepository.findBySkillIgnoreCase(skill);
        if (questions.isEmpty()) throw new NoSuchElementException("No assessment questions are available for: " + skill);
        Map<Long, AssessmentQuestion> byId=new HashMap<>();
        questions.forEach(q -> byId.put(q.getId(), q));
        Set<Long> seen=new HashSet<>(); int correct=0;
        for (AssessmentSubmissionDto.Answer answer : submission.answers()) {
            if (!seen.add(answer.questionId())) throw new IllegalArgumentException("A question cannot be answered more than once");
            AssessmentQuestion q=byId.get(answer.questionId());
            if (q == null) throw new IllegalArgumentException("Submitted question does not belong to this skill assessment");
            String selected=answer.selectedOption().trim().toUpperCase(Locale.ROOT);
            if (!Set.of("A","B","C","D").contains(selected)) throw new IllegalArgumentException("Selected option must be A, B, C, or D");
            if (q.getCorrectOption().equalsIgnoreCase(selected)) correct++;
        }
        if (seen.size()!=questions.size()) throw new IllegalArgumentException("Please answer every question in the assessment");
        User student=userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Authenticated user was not found"));
        return AssessmentResultDto.from(resultRepository.save(new AssessmentResult(student, skill, questions.size(), correct)));
    }
    @Transactional(readOnly = true)
    public List<AssessmentResultDto> getMyResults(String email) {
        return resultRepository.findByStudentEmailOrderBySubmittedAtDesc(email).stream().map(AssessmentResultDto::from).toList();
    }
}
