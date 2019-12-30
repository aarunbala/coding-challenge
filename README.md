# Coding-challenge
Requirement is to calculate the relative balance for an account within the specified time frame.

com.challenge.coding.BalanceCalculator - Initializes the application with the sample data & calls the service class.
com.challenge.coding.TransactionService - Service Implementation class that provides the Business logic.

To Run the application
```bash
mvn clean install
```
OR
```bash
mvn clean install
java -jar target/transaction-service.jar
```

To Run tests
```bash
mvn clean install
```

Console Output
```
Input Account:
ACC998877
From date:
20/10/2018 17:00:00
To date:
20/10/2018 18:00:01
Relative Balance       : -5.0
Number of transactions : 1
```
