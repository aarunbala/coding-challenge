package com.challenge.coding.util;

import com.challenge.coding.exception.InvalidCSVFileException;
import com.challenge.coding.model.Transaction;
import com.challenge.coding.model.TransactionType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionUtils {

    private static final String CSV_SPLITTER = ",";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Method reads the csv at the provided path and converts them into a List<Transaction> objects.
     *
     * @param path                   - Path cannot be null as stated in Assumptions, otherwise null checks need to be added.
     * @param reversedTransactionIds - passing the reference to reversed transaction Ids, so as to populate it.
     * @return List of transactions read from the CSV
     * @throws IOException
     */
    public static List<Transaction> readCSV(String path, List<String> reversedTransactionIds) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        String row;

        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath + path);
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath + path));
        while ((row = csvReader.readLine()) != null) {
            String[] transData = row.split(CSV_SPLITTER);

            Transaction transaction = convertToTransaction(transData, reversedTransactionIds);
            if (transaction != null) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    /**
     * Method converts the raw string array into Transaction object. Also upates the passed reversedTransactionIds
     * with the Transactions that were REVERSED, so that It can be used to exclude the reversed transactions while
     * performing balance calculations.
     *
     * @param trans                  - raw string array containing the values of a transaction
     * @param reversedTransactionIds - list to maintain the reveresedTransactionIds
     * @return
     * @throws InvalidCSVFileException
     */
    public static Transaction convertToTransaction(String[] trans, List<String> reversedTransactionIds) throws InvalidCSVFileException {
        //This IF block can be removed as the Assumption states that Input file and records are in valid format.
        if (trans != null && trans.length > 5) {

            String txId = trans[0].trim();
            String fromAccount = trans[1].trim();
            String toAccount = trans[2].trim();
            LocalDateTime dateTime = convertDateTime(trans[3]);
            Double amount = Double.parseDouble(trans[4].trim());
            TransactionType type = TransactionType.valueOf(trans[5].trim());

            Transaction transaction = new Transaction(txId, fromAccount, toAccount, dateTime, amount, type, null);

            if (type.equals(TransactionType.REVERSAL) && trans[6] != null) {
                transaction.setRelatedTransaction(trans[6].trim());
                reversedTransactionIds.add(trans[6].trim());
            }

            return transaction;
        }
        throw new InvalidCSVFileException("CSV file is Invalid");
    }

    /**
     * Method converts the date string to LocalDateTime using the Standard formatter.
     *
     * @param date - String date to be provided in "dd/MM/yyyy HH:mm:ss" format.
     * @return -  LocalDateTime object
     */
    public static LocalDateTime convertDateTime(String date) {
        if (date != null) {
            return LocalDateTime.parse(date.trim(), formatter);
        }
        return null;
    }
}
