package com.example.academiaindustryportal.portfolio;

import com.example.academiaindustryportal.dto.PortfolioItemDto;
import com.example.academiaindustryportal.entity.PortfolioItem;
import com.example.academiaindustryportal.entity.User;
import com.example.academiaindustryportal.repository.PortfolioItemRepository;
import com.example.academiaindustryportal.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class PortfolioService {
    private static final Set<String> TYPES = Set.of("PROJECT", "CERTIFICATE", "INTERNSHIP", "ACHIEVEMENT", "RESEARCH");
    private final PortfolioItemRepository portfolioRepository;
    private final UserRepository userRepository;
    public PortfolioService(PortfolioItemRepository portfolioRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository; this.userRepository = userRepository;
    }
    @Transactional(readOnly = true)
    public List<PortfolioItemDto> mine(String email) {
        return portfolioRepository.findByStudentEmailOrderByCreatedAtDesc(email).stream().map(PortfolioItemDto::from).toList();
    }
    @Transactional
    public PortfolioItemDto create(String email, PortfolioItemDto dto) {
        User student = findStudent(email); validate(dto);
        PortfolioItem item = new PortfolioItem(); item.setStudent(student); copy(dto, item);
        return PortfolioItemDto.from(portfolioRepository.save(item));
    }
    @Transactional
    public PortfolioItemDto update(String email, Long id, PortfolioItemDto dto) {
        PortfolioItem item = owned(email, id); validate(dto); copy(dto, item);
        return PortfolioItemDto.from(portfolioRepository.save(item));
    }
    @Transactional
    public void delete(String email, Long id) { portfolioRepository.delete(owned(email, id)); }
    private User findStudent(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getRole() != com.example.academiaindustryportal.entity.Role.ROLE_STUDENT)
            throw new AccessDeniedException("Only student accounts can manage student portfolios");
        return user;
    }
    private PortfolioItem owned(String email, Long id) {
        findStudent(email);
        PortfolioItem item = portfolioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Portfolio item not found"));
        if (!item.getStudent().getEmail().equalsIgnoreCase(email)) throw new AccessDeniedException("You can only modify your own portfolio items");
        return item;
    }
    private void validate(PortfolioItemDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) throw new IllegalArgumentException("Portfolio title is required");
        if (dto.getTitle().trim().length() > 120) throw new IllegalArgumentException("Title must be 120 characters or fewer");
        if (dto.getDescription() != null && dto.getDescription().length() > 1200) throw new IllegalArgumentException("Description must be 1200 characters or fewer");
        if (dto.getUrl() != null && !dto.getUrl().isBlank() && !(dto.getUrl().startsWith("https://") || dto.getUrl().startsWith("http://"))) throw new IllegalArgumentException("Evidence link must start with http:// or https://");
        String type = dto.getType() == null ? "PROJECT" : dto.getType().trim().toUpperCase(Locale.ROOT);
        if (!TYPES.contains(type)) throw new IllegalArgumentException("Unsupported portfolio item type");
        dto.setType(type.substring(0, 1) + type.substring(1).toLowerCase(Locale.ROOT));
    }
    private void copy(PortfolioItemDto dto, PortfolioItem item) {
        item.setType(dto.getType()); item.setTitle(dto.getTitle().trim());
        item.setDescription(dto.getDescription() == null ? "" : dto.getDescription().trim());
        item.setUrl(dto.getUrl() == null ? "" : dto.getUrl().trim()); item.setDate(dto.getDate() == null ? "" : dto.getDate().trim());
    }
}
