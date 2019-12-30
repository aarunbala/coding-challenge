package com.challenge.coding.util;

import com.challenge.coding.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static List<Transaction> readCSV(URL path) {

        CsvMapper mapper = new CsvMapper();

        try {
            MappingIterator<Transaction> iterator = mapper.registerModule(new JavaTimeModule()).readerFor(Transaction.class).with(CsvSchema.emptySchema().withHeader()).readValues(path);
            return iterator.readAll();
        } catch (IOException e) {
            throw new RuntimeException("Parsing exception");
        }
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
