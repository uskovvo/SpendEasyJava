package com.app.spendeasyjava.domain.repositories;

import com.app.spendeasyjava.domain.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, UUID> {

    List<Categories> findAllByUserId(UUID userId);


}
