package com.app.spendeasyjava.service.interfaces;

import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.User;


import java.util.List;
import java.util.UUID;

public interface CategoriesService {

    List<Categories> createDefaultCategories(User user);
    List<Categories> getAllCategories(UUID userId);
    Categories createCustomCategory(Categories category);
    Categories getCategory(UUID categoryId);
    Categories updateCategory(UUID categoryId);
    Categories deleteCategory(UUID categoryId);
    Long getTotalAmountByCategory(UUID categoryId);
}
