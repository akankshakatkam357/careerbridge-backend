package com.example.academiaindustryportal.config;

import com.example.academiaindustryportal.entity.*;
import com.example.academiaindustryportal.repository.OpportunityRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Optional bootstrap data.
 *  - APP_ADMIN_EMAIL / APP_ADMIN_PASSWORD: creates an admin account if it does not exist.
 *  - APP_SEED_ENABLED=true (on by default in the "dev" profile): creates demo users and sample opportunities.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    public static final String DEMO_PASSWORD = "Password@123";

    private final UserRepository userRepository;
    private final OpportunityRepository opportunityRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    @Value("${app.admin.email:}")
    private String adminEmail;

    @Value("${app.admin.password:}")
    private String adminPassword;

    public DataSeeder(UserRepository userRepository,
                      OpportunityRepository opportunityRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.opportunityRepository = opportunityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!adminEmail.isBlank() && !adminPassword.isBlank()) {
            createUserIfMissing(adminEmail.trim().toLowerCase(), adminPassword, "Administrator", Role.ROLE_ADMIN);
        }

        if (!seedEnabled) {
            return;
        }

        createUserIfMissing("admin@demo.com", DEMO_PASSWORD, "Demo Admin", Role.ROLE_ADMIN);
        createUserIfMissing("student@demo.com", DEMO_PASSWORD, "Demo Student", Role.ROLE_STUDENT);
        createUserIfMissing("faculty@demo.com", DEMO_PASSWORD, "Demo Faculty", Role.ROLE_FACULTY);
        User industry = createUserIfMissing("industry@demo.com", DEMO_PASSWORD, "Demo Industry", Role.ROLE_INDUSTRY);

        if (opportunityRepository.count() == 0) {
            addOpportunity(industry, "Java Backend Developer", "Build REST APIs with Spring Boot.",
                    "Technology", "Acme Software", "Hyderabad", OpportunityType.JOB, "Java", 4, "Spring", 3);
            addOpportunity(industry, "Frontend Intern", "Work on React dashboards with our product team.",
                    "Technology", "Acme Software", "Remote", OpportunityType.INTERNSHIP, "React", 3, "JavaScript", 3);
        }
        log.info("Demo data ready. Log in with e.g. student@demo.com / {}", DEMO_PASSWORD);
    }

    private User createUserIfMissing(String email, String password, String fullName, Role role) {
        return userRepository.findByEmail(email).orElseGet(
                () -> userRepository.save(new User(email, passwordEncoder.encode(password), fullName, role)));
    }

    private void addOpportunity(User poster, String title, String description, String domain, String company,
                                String location, OpportunityType type,
                                String skill1, int level1, String skill2, int level2) {
        Opportunity opportunity = new Opportunity(title, description, domain, company, location, type, poster);
        opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, skill1, level1));
        opportunity.getRequiredSkills().add(new OpportunitySkill(opportunity, skill2, level2));
        opportunityRepository.save(opportunity);
    }
}
