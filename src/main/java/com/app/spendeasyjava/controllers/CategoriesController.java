package com.app.spendeasyjava.controllers;

import com.app.spendeasyjava.domain.DTO.CategoriesDTO;
import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.requests.UpdateCategoryRequest;
import com.app.spendeasyjava.domain.responses.Response;
import com.app.spendeasyjava.service.CategoriesServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesServiceImpl categoriesService;

    @GetMapping
    public ResponseEntity<?> getAllCategoriesByUser(Principal user) {
        Response response = new Response("Success", categoriesService.getAllCategories(user));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable UUID categoryId) {
        try {
            CategoriesDTO categoriesDTO = categoriesService.getCategoryById(categoryId);
            return ResponseEntity.ok(categoriesDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createCustomCategory(@RequestParam String nameCategory, Principal user) {
        Response response = new Response("Success", categoriesService.createCustomCategory(user, nameCategory));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        try {
            categoriesService.updateCategory(categoryId, updateCategoryRequest);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
