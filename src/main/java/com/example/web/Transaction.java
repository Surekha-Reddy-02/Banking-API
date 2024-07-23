package com.example.web;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private long accountNo;
    private String description;
    private double amount;
    private double balance;
    private Timestamp transactionDate;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getAccountNo() { return accountNo; }
    public void setAccountNo(long accountNo) { this.accountNo = accountNo; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountNo=" + accountNo +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
