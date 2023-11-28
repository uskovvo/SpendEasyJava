package com.app.spendeasyjava.service;

import com.app.spendeasyjava.domain.DTO.TransactionDto;
import com.app.spendeasyjava.domain.entities.Accounts;
import com.app.spendeasyjava.domain.entities.Categories;
import com.app.spendeasyjava.domain.entities.Transactions;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.repositories.TransactionsRepository;
import com.app.spendeasyjava.domain.requests.TransactionRequest;
import com.app.spendeasyjava.service.interfaces.TransactionsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final CategoriesServiceImpl categoriesService;
    private final UserServiceImpl userService;

    public List<TransactionDto> getAllTransactionsByCategory(UUID categoryId) {
        Categories category = categoriesService.getCategoryById(categoryId);
        return transactionsRepository
                .findAllByCategory(category)
                .stream()
                .map(TransactionDto::toDto)
                .collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(UUID transactionId) {
        return TransactionDto
                .toDto(transactionsRepository
                        .findById(transactionId)
                        .orElseThrow(EntityNotFoundException::new));
    }

    public TransactionDto createNewTransaction(TransactionRequest transactionRequest, Principal connectedUser) {
        User user = userService.getUser(connectedUser);
        Categories category = transactionRequest.getCategoryId() == null ?
                categoriesService.getCategoryByUserAndByName("Other", user) :
                categoriesService.getCategoryById(transactionRequest.getCategoryId());
        Transactions transaction = Transactions
                .builder()
                .transactionType(transactionRequest.getTransactionType())
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createDateTime(LocalDateTime.now())
                .category(category)
                .user(user)
                .build();

        return TransactionDto.toDto(transactionsRepository.save(transaction));
    }

    public List<TransactionDto> getAllTransactionsByUser(Principal connectedUser) {
        User user = userService.getUser(connectedUser);
        return transactionsRepository.findAllByUser(user).stream().map(TransactionDto::toDto).collect(Collectors.toList());
    }
}
