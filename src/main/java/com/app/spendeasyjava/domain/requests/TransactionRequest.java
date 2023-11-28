package com.app.spendeasyjava.domain.requests;

import com.app.spendeasyjava.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private UUID categoryId;
//    private UUID accountId;
}
