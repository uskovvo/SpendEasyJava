package com.app.spendeasyjava.domain.DTO;

import com.app.spendeasyjava.domain.entities.Transactions;
import com.app.spendeasyjava.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime createDateTime;
    private String description;

    public static TransactionDto toDto(Transactions transaction){
        return TransactionDto
                .builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .createDateTime(transaction.getCreateDateTime())
                .description(transaction.getDescription())
                .build();
    }
}
