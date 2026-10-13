package com.example.academiaindustryportal.controller;

import com.example.academiaindustryportal.dto.ApiResponse;
import com.example.academiaindustryportal.dto.PortfolioItemDto;
import com.example.academiaindustryportal.portfolio.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@PreAuthorize("hasAuthority('ROLE_STUDENT')")
public class PortfolioController {
    private final PortfolioService service;
    public PortfolioController(PortfolioService service) { this.service = service; }
    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<PortfolioItemDto>>> mine(Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Portfolio loaded", service.mine(auth.getName())));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<PortfolioItemDto>> create(@RequestBody PortfolioItemDto dto, Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Portfolio item added", service.create(auth.getName(), dto)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PortfolioItemDto>> update(@PathVariable Long id, @RequestBody PortfolioItemDto dto, Authentication auth) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Portfolio item updated", service.update(auth.getName(), id, dto)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication auth) {
        service.delete(auth.getName(), id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Portfolio item deleted", null));
    }
}
