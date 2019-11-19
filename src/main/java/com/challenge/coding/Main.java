package com.challenge.coding;

import com.challenge.coding.exception.InvalidInputException;

public class Main {

    public static void main(String[] args) {
        //Path need to be changed based on the system where the program is run.
        String csvPath = "/src/main/resources/sample.csv";


        // Input parameters for balance calculation.
//        String accountId = "ACC334455";
//        String fromDateTime = "20/10/2018 12:00:00";
//        String toDateTime = "20/10/2018 19:00:00";

        String accountId = "ACC998877";
        String fromDateTime = "20/10/2018 18:00:00";
        String toDateTime = "20/10/2018 18:00:00";

        TransactionService service = new TransactionService();

        service.loadCSV(csvPath);
        try {
            service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);
        } catch (InvalidInputException e) {
            System.err.println(e.getMessage());
        }
    }
}
