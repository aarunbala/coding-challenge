package com.challenge.coding;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;

public interface ITransactionService {

    DoubleSummaryStatistics calculateRelativeBalance(String accountId, LocalDateTime from, LocalDateTime to);
    DoubleSummaryStatistics calculateRelativeBalance(String accountId, String from, String to);
}
