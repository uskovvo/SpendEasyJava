package com.app.spendeasyjava.domain.repositories;

import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.enums.CategoriesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, UUID> {

    List<Categories> findAllByUser(User user);
    Optional<Categories> findById(UUID categoryId);
    List<Categories> findCategoriesByUserAndCategoriesType(User user, CategoriesType categoriesType);

    Optional<Categories> findByNameAndUser(String name, User user);
}
