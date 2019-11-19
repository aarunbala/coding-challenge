# coding-challenge
[To Run]
"mvn clean install"

Main.main() - runs with a single default input parameter.

TransactionServiceTest - contains tests that run with a few different parameters

[Approach:]
1. Load the CSV into memory (temporary solution)
2. Identify the Reversed transaction Ids while loading the data
3. Based on the inputs passed, filter transactions and calculate relative balance

[Assumptions:]
1. If only REVERSAL transactions are available for the provided timeframe, then they are considered for Balance calculation.
2. SysOut are used temporarily instead of loggers.
3. Test classes are created only for testing the functionality. Not created for the Util/support classes.
