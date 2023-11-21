package com.app.spendeasyjava.service.interfaces;

import com.app.spendeasyjava.domain.DTO.CategoriesDTO;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.requests.UpdateCategoryRequest;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface CategoriesService {

    void createDefaultCategories(User user);
    List<CategoriesDTO> getAllCategories(Principal user);
    CategoriesDTO createCustomCategory(Principal user, String nameCategory);
    CategoriesDTO getCategoryById(UUID categoryId);
    CategoriesDTO updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest);
    void deleteCategory(UUID categoryId);
    Long getTotalAmountByCategory(UUID categoryId);
}
