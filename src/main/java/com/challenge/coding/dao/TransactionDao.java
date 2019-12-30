package com.challenge.coding.dao;

import com.challenge.coding.model.Transaction;
import com.challenge.coding.util.TransactionUtils;
import lombok.AllArgsConstructor;

import java.net.URL;
import java.util.List;

@AllArgsConstructor
public class TransactionDao {

    private URL path;

    public List<Transaction> getTransactions(){
        return TransactionUtils.readCSV(path);
    }
}
