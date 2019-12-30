package com.challenge.coding;

import com.challenge.coding.dao.TransactionDao;

import java.net.URL;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class BalanceCalculator {

    public static void main(String[] args) {
        BalanceCalculator balanceCalculator = new BalanceCalculator();
        balanceCalculator.calculateRelativeBalance();
    }

    public void calculateRelativeBalance() {

        URL url = getClass().getClassLoader().getResource("sample.csv");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Account:");
        String accountId = scanner.nextLine().trim();
        System.out.println("From date:");
        String fromDateTime = scanner.nextLine().trim();
        System.out.println("To date:");
        String toDateTime = scanner.nextLine().trim();

        TransactionDao dao = new TransactionDao(url);
        TransactionService service = new TransactionService(dao);

        DoubleSummaryStatistics stats = service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);

        System.out.println("Relative Balance       : " + stats.getSum());
        System.out.println("Number of transactions : " + stats.getCount());

    }
}
