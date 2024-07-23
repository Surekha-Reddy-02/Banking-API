package com.example.model;

import com.example.web.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewTransactions")
public class ViewTransactionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Example logic to get transactions
        List<Transaction> transactions = getTransactions(); // Replace with your actual method to fetch transactions

        // Set the transactions list in the request
        request.setAttribute("transactions", transactions);

        // Forward to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewTransactions.jsp");
        dispatcher.forward(request, response);
    }

    private List<Transaction> getTransactions() {
        // Mock data for demonstration
        List<Transaction> transactions = new ArrayList<>();
        
        Transaction txn1 = new Transaction();
        txn1.setId(1);
        txn1.setAccountNo(123456789L);
        txn1.setDescription("Deposit");
        txn1.setAmount(1000.00);
        txn1.setBalance(5000.00);
        txn1.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transactions.add(txn1);
        
        Transaction txn2 = new Transaction();
        txn2.setId(2);
        txn2.setAccountNo(123456789L);
        txn2.setDescription("Withdrawal");
        txn2.setAmount(-200.00);
        txn2.setBalance(4800.00);
        txn2.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transactions.add(txn2);
        
        return transactions; // Replace with actual data fetching logic
    }
}
