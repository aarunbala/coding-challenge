package com.challenge.coding;

import com.challenge.coding.dao.TransactionDao;
import com.challenge.coding.util.TransactionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.DoubleSummaryStatistics;

public class TransactionServiceTest {

    private TransactionService service;
    private TransactionDao dao;

    @Before
    public void setup() {
        URL url = getClass().getClassLoader().getResource("sample.csv");
        dao = new TransactionDao(url);
        service = new TransactionService(dao);
    }

    @Test
    public void testcalculateRelativeBalance_withReversalTransactions() {
        String accountId = "ACC334455";
        String fromDateTime = "20/10/2018 12:00:00";
        String toDateTime = "20/10/2018 19:00:00";

        DoubleSummaryStatistics relativeBalance = service.calculateRelativeBalance(accountId, TransactionUtils.convertDateTime(fromDateTime), TransactionUtils.convertDateTime(toDateTime));

        Assert.assertEquals(1, relativeBalance.getCount());
        Assert.assertEquals(-25.0, relativeBalance.getSum(),0);
    }

    @Test
    public void testcalculateRelativeBalance_withMultipleTransactions() {
        String accountId = "ACC778899";
        String fromDateTime = "20/10/2018 12:00:00";
        String toDateTime = "21/10/2018 09:30:01";

        DoubleSummaryStatistics relativeBalance = service.calculateRelativeBalance(accountId, TransactionUtils.convertDateTime(fromDateTime), TransactionUtils.convertDateTime(toDateTime));

        Assert.assertEquals(3, relativeBalance.getCount());
        Assert.assertEquals(37.25, relativeBalance.getSum(), 0);
    }

    @Test
    public void testcalculateRelativeBalance_withOneTransaction() {
        String accountId = "ACC998877";
        String fromDateTime = "20/10/2018 17:00:00";
        String toDateTime = "20/10/2018 18:00:01";

        DoubleSummaryStatistics relativeBalance = service.calculateRelativeBalance(accountId, fromDateTime, toDateTime);

        Assert.assertEquals(1, relativeBalance.getCount());
        Assert.assertEquals(-5.0, relativeBalance.getSum(), 0);
    }

    @Test
    public void testcalculateRelativeBalance_withOnlyReversalTransactions() {
        String accountId = "ACC998877";
        String fromDateTime = "20/10/2018 19:45:00";
        String toDateTime = "20/10/2018 19:45:00";

        DoubleSummaryStatistics relativeBalance = service.calculateRelativeBalance(accountId, TransactionUtils.convertDateTime(fromDateTime), TransactionUtils.convertDateTime(toDateTime));

        Assert.assertEquals(0, relativeBalance.getCount());
        Assert.assertEquals(0, relativeBalance.getSum(), 0);
    }

    @After
    public void tearDown() {
    }
}
