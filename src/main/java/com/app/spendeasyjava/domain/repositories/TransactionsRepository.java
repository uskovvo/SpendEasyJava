package com.app.spendeasyjava.domain.repositories;

import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.Transactions;
import com.app.spendeasyjava.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

    List<Transactions> findAllByCategory(Categories category);
    List<Transactions> findAllByUser(User user);
}
