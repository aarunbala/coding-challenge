# Coding-challenge
To Run
```bash
mvn clean install
```

Main.balanceCalculator() - runs with a single set of input parameters as described in the challenge document.

TransactionServiceTest - contains tests that run with a few different sets of parameters.

## Approach
1. Load the CSV into memory (temporary solution)
2. Identify the Reversed transaction Ids while loading the data
3. Based on the inputs passed, filter transactions and calculate the relative balance

## Assumptions
1. If REVERSAL transactions are available for the provided timeframe, then they are considered for Balance calculation.
2. SysOut is used temporarily instead of loggers.
3. Test classes are created only for testing the functionality. Not created for the Util/support classes.
