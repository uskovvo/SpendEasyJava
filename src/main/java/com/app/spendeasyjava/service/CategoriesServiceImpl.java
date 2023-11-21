package com.app.spendeasyjava.service;


import com.app.spendeasyjava.domain.DTO.CategoriesDTO;
import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.enums.CategoriesType;
import com.app.spendeasyjava.domain.enums.DefaultCategories;
import com.app.spendeasyjava.domain.repositories.CategoriesRepository;
import com.app.spendeasyjava.domain.requests.UpdateCategoryRequest;
import com.app.spendeasyjava.service.interfaces.CategoriesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final UserService userService;

    public void createDefaultCategories(User user) {
        for (DefaultCategories defaultCategory : DefaultCategories.values()) {
            Categories category = Categories.builder()
                    .name(defaultCategory.getCategory())
                    .categoriesType(CategoriesType.DEFAULT)
                    .user(user)
                    .build();

            categoriesRepository.save(category);
        }
    }

    public List<CategoriesDTO> getAllCategories(Principal connectedUser) {
        User user = userService.getUser(connectedUser);
        List<CategoriesDTO> categoriesDTOList = new ArrayList<>();
        categoriesRepository.findAllByUser(user).forEach(c -> categoriesDTOList.add(CategoriesDTO.toDto(c)));
        return categoriesDTOList;
    }

    @Override
    public CategoriesDTO createCustomCategory(Principal authUser, String nameCategory) {
        User user = userService.getUser(authUser);
        Categories category = Categories
                .builder()
                .name(nameCategory)
                .categoriesType(CategoriesType.CUSTOM)
                .user(user)
                .build();
        categoriesRepository.save(category);
        return CategoriesDTO.toDto(category);
    }

    @Override
    public CategoriesDTO updateCategory(UUID categoryId, UpdateCategoryRequest updateCategoryRequest) {
        Categories category = categoriesRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found"
                ));
        category.setName(updateCategoryRequest.getName());
        categoriesRepository.save(category);
        return CategoriesDTO.toDto(category);
    }

    @Override
    public void deleteCategory(UUID categoryId) {

    }

    @Override
    public Long getTotalAmountByCategory(UUID categoryId) {
        return null;
    }

    public CategoriesDTO getCategoryById(UUID categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return CategoriesDTO.toDto(category);
    }
}
