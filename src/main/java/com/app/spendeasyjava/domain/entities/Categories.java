package com.app.spendeasyjava.domain.entities;

import com.app.spendeasyjava.domain.enums.CategoriesType;
import com.app.spendeasyjava.domain.enums.DefaultCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoriesType categoriesType;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Transactions> transactions;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Budget> budgets;

}
