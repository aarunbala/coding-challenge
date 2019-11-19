package com.challenge.coding;

import com.challenge.coding.exception.InvalidInputException;
import com.challenge.coding.model.Transaction;
import com.challenge.coding.model.TransactionType;
import com.challenge.coding.util.TransactionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that does provide the functionality for CSV loading & Balance Calculations.
 */
public class TransactionService {

    private List<Transaction> transactions = new ArrayList<>();
    private List<String> reversedTransactionIds = new ArrayList<>();


    /**
     * Method utilises the UTIL methods to load the CSV and retains them as List<Transaction> object.
     *
     * @param path - Path to the CSV, Mandatory
     */
    public void loadCSV(String path) {
        try {
            transactions = TransactionUtils.readCSV(path, reversedTransactionIds);
        } catch (IOException e) {
            System.out.println("Invalid CSV file / location");
            System.exit(0);
        }
        // Ideally should be using Logger, but the focus of this exercise is on logic, hence using SysOut temporarily.
        System.out.println("Loaded Transactions \n" + transactions);
    }



    /**
     * Method filters the transactions and calculates the relative balance for an account.
     *
     * @param accountId    - Mandatory
     * @param fromDateTime - From the time the transactions need to be included; Optional, otherwise all the
     *                     transactions for the account will be taken for calculating balance
     * @param toDateTime   - till the time, the transactions need to be included; Optional, otherwise all the
     *                     transactions for the account will be taken for calculating balance
     */
    public Double calculateRelativeBalance(String accountId, String fromDateTime, String toDateTime) throws InvalidInputException {

        if (accountId == null) {
            throw new InvalidInputException("Account ID should be provided"); //throwing this - with an REST API mindset - instead of BadRequest.
        }

        LocalDateTime from = fromDateTime != null ? TransactionUtils.convertDateTime(fromDateTime) : null;
        LocalDateTime to = toDateTime != null ? TransactionUtils.convertDateTime(toDateTime) : null;

        // Added this method initially to identify the reversed transaction Ids;
        // Below line will add O log(n) to the complexity, hence commented out and identifying these when loading the CSV.
        //List<String> reversedTransactionIds = filterOutReversedTransactionIds(transactions);

        List<Transaction> filteredTransactions = filterTransactionsByAccountAndTime(accountId, from, to, reversedTransactionIds);
        System.out.println("Filtered Transactions \n" + filteredTransactions);

        Double balance = 0.0;
        if (filteredTransactions.size() > 0) {
            balance = calculateBalance(filteredTransactions, accountId);
        }
        System.out.println("Account Number is " + accountId);
        System.out.println("From : " + fromDateTime + "   To : " + toDateTime);
        System.out.println("Relative Balance for the Period is " + balance);
        System.out.println("Number of Transactions included is " + filteredTransactions.size());
        return balance;
    }



    /**
     * Method filters the transactions based on the parameters provided.
     *
     * @param accountId              - accountId for which transactions need to be filtered for calculating balance, Mandatory
     * @param from                   - FromDateTime; If not provided, will include all the transactions for balance calculation
     * @param to                     - ToDateTime; If not provided, will include all the transactions for balance calculation
     * @param reversedTransactionIds
     * @return
     */
    private List<Transaction> filterTransactionsByAccountAndTime(String accountId, LocalDateTime from, LocalDateTime to, List<String> reversedTransactionIds) {
        return transactions
                .stream()
                .filter(transaction -> (!reversedTransactionIds.contains(transaction.getTransactionId())
                        && (transaction.getFromAccountId().equals(accountId) || transaction.getToAccountId().equals(accountId))
                        && (to == null || transaction.getCreatedAt().isBefore(to) || transaction.getCreatedAt().isEqual(to))
                        && (from == null || transaction.getCreatedAt().isAfter(from) || transaction.getCreatedAt().isEqual(from))))
                .collect(Collectors.toList());
    }



    /**
     * Method calculates the balance based on the parameters like toAccount/fromAccount
     * and PAYMENT/REVERSAL.
     *
     * @param filteredTransactions - transactions filtered for accountId, from, to criteria
     * @param accountId            - accountId
     * @return
     */
    private Double calculateBalance(List<Transaction> filteredTransactions, String accountId) {
        Double balance = 0.0;
        for (Transaction transaction : filteredTransactions) {
            if (transaction.getToAccountId().equals(accountId)) {
                if (transaction.getTransactionType().equals(TransactionType.REVERSAL)) {
                    balance -= transaction.getAmount();
                } else {
                    balance += transaction.getAmount();
                }
            } else if (transaction.getFromAccountId().equals(accountId)) {
                if (transaction.getTransactionType().equals(TransactionType.REVERSAL)) {
                    balance += transaction.getAmount();
                } else {
                    balance -= transaction.getAmount();
                }
            }
        }
        return balance;
    }

    /*
    private List<String> filterOutReversedTransactionIds(List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> (transaction.getTransactionType().equals(TransactionType.REVERSAL)))
                .map(transaction -> transaction.getRelatedTransaction())
                .collect(Collectors.toList());
    }
    */
}
