package com.app.spendeasyjava.controllers;

import com.app.spendeasyjava.domain.requests.TransactionRequest;
import com.app.spendeasyjava.domain.responses.Response;
import com.app.spendeasyjava.service.TransactionsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionsServiceImpl transactionsService;

    @GetMapping
    public ResponseEntity<?> getAllTransactions(Principal connectedUser) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(HttpStatus.OK.name(),
                        transactionsService.getAllTransactionsByUser(connectedUser)));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getAllTransactionsByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(HttpStatus.OK.name(),
                        transactionsService.getAllTransactionsByCategory(categoryId)));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID transactionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(HttpStatus.OK.name(),
                        transactionsService.getTransactionById(transactionId)));
    }

    @PostMapping
    public ResponseEntity<?> createNewTransaction(TransactionRequest transactionRequest, Principal connectedUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(HttpStatus.OK.name(),
                        transactionsService.createNewTransaction(transactionRequest, connectedUser)));
    }

    @GetMapping("/total_amount")
    public ResponseEntity<?> getUsersExpenseTotalAmount(Principal connectedUser) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(HttpStatus.OK.name(),
                        transactionsService.getUsersExpenseTotalAmount(connectedUser)));
    }
}
