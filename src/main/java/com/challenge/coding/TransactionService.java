package com.challenge.coding;

import com.challenge.coding.dao.TransactionDao;
import com.challenge.coding.model.Transaction;
import com.challenge.coding.model.TransactionType;

import java.time.LocalDateTime;
import java.util.*;

import static com.challenge.coding.util.TransactionUtils.convertDateTime;

/**
 * Service class that does provide the functionality - Balance Calculations.
 */
public class TransactionService implements ITransactionService {

    private TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public DoubleSummaryStatistics calculateRelativeBalance(String accountId, LocalDateTime from, LocalDateTime to) {
        List<Transaction> transactions = transactionDao.getTransactions();
        Collections.reverse(transactions);
        Set<String> reversedTranxIds = new HashSet<>();
        DoubleSummaryStatistics stats = transactions.stream()
                .filter(transaction -> (transaction.getFromAccountId().equals(accountId) || transaction.getToAccountId().equals(accountId)))
                .filter(transaction -> {
                    if (transaction.getTransactionType().equals(TransactionType.REVERSAL)) {
                        reversedTranxIds.add(transaction.getRelatedTransaction());
                        return false;
                    } else {
                        return true;
                    }
                })
                .filter(transaction ->
                        !reversedTranxIds.contains(transaction.getTransactionId())
                                && (to == null || transaction.getCreatedAt().isBefore(to) || transaction.getCreatedAt().isEqual(to))
                                && (from == null || transaction.getCreatedAt().isAfter(from) || transaction.getCreatedAt().isEqual(from)))
                .mapToDouble(transaction -> {
                    if (transaction.getFromAccountId().equals(accountId)) {
                        return -transaction.getAmount();
                    } else {
                        return transaction.getAmount();
                    }
                }).summaryStatistics();
        return stats;
    }

    @Override
    public DoubleSummaryStatistics calculateRelativeBalance(String accountId, String from, String to) {
        return calculateRelativeBalance(accountId,convertDateTime(from), convertDateTime(to));
    }

}
