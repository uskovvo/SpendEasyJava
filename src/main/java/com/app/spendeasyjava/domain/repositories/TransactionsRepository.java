package com.app.spendeasyjava.domain.repositories;

import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.Transactions;
import com.app.spendeasyjava.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

    List<Transactions> findAllByCategory(Categories category);
    List<Transactions> findAllByUser(User user);

    @Query("select sum(t.amount) from Transactions t where t.user = :user and t.transactionType = 'EXPENSE'")
    BigDecimal getTotalAmount(User user);
}
