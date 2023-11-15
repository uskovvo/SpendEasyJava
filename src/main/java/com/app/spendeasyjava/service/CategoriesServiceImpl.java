package com.app.spendeasyjava.service;


import com.app.spendeasyjava.domain.DTO.CategoriesDTO;
import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.enums.CategoriesType;
import com.app.spendeasyjava.domain.enums.DefaultCategories;
import com.app.spendeasyjava.domain.repositories.CategoriesRepository;
import com.app.spendeasyjava.service.interfaces.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository repository;

    @Override
    public List<Categories> createDefaultCategories(User user) {
        List<Categories> categories = new ArrayList<>();

        for (DefaultCategories defCat : DefaultCategories.values()){
            Categories category = new Categories();
            category.setName(defCat.name());
            category.setCategoriesType(CategoriesType.DEFAULT);
            category.setUser(user);
            categories.add(category);
        }
        repository.saveAll(categories);
        return categories;
    }

    @Override
    public List<Categories> getAllCategories(UUID userId) {
        System.out.println(userId);
        //        categoriesList.forEach(System.out::println);
        return repository.findAllByUserId(userId);
    }

    private List<CategoriesDTO> mapCategoriesToDto(List<Categories> categoriesList) {
        List<CategoriesDTO> categoriesDTOS = new ArrayList<>();
        categoriesList.forEach(c -> {
            categoriesDTOS.add(CategoriesDTO.toDto(c));
        });

        return categoriesDTOS;
    }

    @Override
    public Categories createCustomCategory(Categories category) {
        return null;
    }

    @Override
    public Categories getCategory(UUID categoryId) {
        return null;
    }

    @Override
    public Categories updateCategory(UUID categoryId) {
        return null;
    }

    @Override
    public Categories deleteCategory(UUID categoryId) {
        return null;
    }

    @Override
    public Long getTotalAmountByCategory(UUID categoryId) {
        return null;
    }
}
