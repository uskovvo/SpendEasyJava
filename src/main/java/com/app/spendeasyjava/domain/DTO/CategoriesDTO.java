package com.app.spendeasyjava.domain.DTO;


import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.enums.CategoriesType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDTO {

    private UUID id;
    private String name;
    private CategoriesType categoriesType;

    public static CategoriesDTO toDto(Categories categories){
        return CategoriesDTO.builder()
                .id(categories.getId())
                .name(categories.getName())
                .categoriesType(categories.getCategoriesType())
                .build();
    }
}
