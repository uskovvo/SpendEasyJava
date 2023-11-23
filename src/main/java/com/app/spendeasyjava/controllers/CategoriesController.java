package com.app.spendeasyjava.controllers;

import com.app.spendeasyjava.domain.DTO.CategoriesDTO;
import com.app.spendeasyjava.domain.enums.Messages;
import com.app.spendeasyjava.domain.requests.UpdateCategoryRequest;
import com.app.spendeasyjava.domain.responses.Response;
import com.app.spendeasyjava.service.CategoriesServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesServiceImpl categoriesService;
    private final MessageSource messageSource;

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
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(messageSource.getMessage(Messages.ENTITY_NOT_FOUND.getMessage(), null, Locale.getDefault()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createCustomCategory(@RequestParam String nameCategory, Principal user) {
        Response response = new Response("Success", categoriesService.createCustomCategory(user, nameCategory));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        try {
            categoriesService.updateCategory(categoryId, updateCategoryRequest);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(messageSource.getMessage(Messages.ENTITY_NOT_FOUND.getMessage(), null, Locale.getDefault()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@RequestParam UUID categoryId) {
        try {
            categoriesService.deleteCategory(categoryId);
            return ResponseEntity.ok(messageSource.getMessage(Messages.DELETED.getMessage(), null, Locale.getDefault()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(messageSource.getMessage(Messages.ENTITY_NOT_FOUND.getMessage(), null, Locale.getDefault()));
        }
    }

}
