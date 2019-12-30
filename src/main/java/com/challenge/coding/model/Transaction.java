package com.challenge.coding.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Model object to retain the transactions.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Transaction {
    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private Double amount;
    private TransactionType transactionType;
    private String relatedTransaction;
}


