package com.challenge.coding;

import com.challenge.coding.exception.InvalidInputException;
import com.challenge.coding.model.Transaction;
import com.challenge.coding.model.TransactionType;
import com.challenge.coding.util.TransactionUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceTest {

    private TransactionService service;

    @Before
    public void setup() {
        service = new TransactionService();
        service.loadCSV("/src/main/resources/sample.csv");
    }

    @Test
    public void testcalculateRelativeBalanceReversal() throws InvalidInputException {
        String accountId = "ACC334455";
        String fromDateTime = "20/10/2018 12:00:00";
        String toDateTime = "20/10/2018 19:00:00";

        Double relativeBalance = service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);

        Assert.assertEquals(new Double(-25.0), relativeBalance);
    }


    @Test
    public void testcalculateRelativeBalance() throws InvalidInputException {
        String accountId = "ACC998877";
        String fromDateTime = "20/10/2018 17:00:00";
        String toDateTime = "20/10/2018 18:00:01";

        Double relativeBalance = service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);

        Assert.assertEquals(new Double(-5.0), relativeBalance);
    }

    @Test
    public void testcalculateRelativeBalanceOnlyReversal() throws InvalidInputException {
        String accountId = "ACC998877";
        String fromDateTime = "20/10/2018 19:45:00";
        String toDateTime = "20/10/2018 19:45:00";

        Double relativeBalance = service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);

        Assert.assertEquals(new Double(-10.5), relativeBalance);
    }

    @Test
    public void testcalculateRelativeBalanceWithoutTime() throws InvalidInputException {
        String accountId = "ACC998877";

        Double relativeBalance = service.calculateRelativeBalance(accountId, null, null);

        Assert.assertEquals(new Double(-15.5), relativeBalance);
    }

}
