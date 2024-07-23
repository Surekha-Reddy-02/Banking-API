package com.example.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gen";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Suryareddy@020";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("customerLogin.jsp?error=Please login first.");
            return;
        }

        String accountNo = (String) session.getAttribute("account_no");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Connection conn = null;
        PreparedStatement updateStmt = null;
        PreparedStatement transactionStmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            conn.setAutoCommit(false);

            // Update balance in the customer table
            String updateQuery = "UPDATE customers SET initial_balance = initial_balance + ? WHERE account_no = ?";
            updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountNo);
            int rowCount = updateStmt.executeUpdate();

            if (rowCount > 0) {
                // Record transaction in the transactions table
                String insertQuery = "INSERT INTO transactions (account_no, description, amount, balance, transaction_date) VALUES (?, ?, ?, ?, ?)";
                transactionStmt = conn.prepareStatement(insertQuery);
                transactionStmt.setString(1, accountNo);
                transactionStmt.setString(2, "Deposit");
                transactionStmt.setDouble(3, amount);

                // Retrieve updated balance
                String selectQuery = "SELECT initial_balance FROM customers WHERE account_no = ?";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                selectStmt.setString(1, accountNo);
                ResultSet rs = selectStmt.executeQuery();
                double balance = 0.0;
                if (rs.next()) {
                    balance = rs.getDouble("initial_balance");
                }
                transactionStmt.setDouble(4, balance);
                transactionStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

                int transactionRowCount = transactionStmt.executeUpdate();

                if (transactionRowCount > 0) {
                    conn.commit();
                    session.setAttribute("initial_balance", balance);
                    response.sendRedirect("customerdashboard.jsp");
                } else {
                    conn.rollback();
                    response.getWriter().println("Transaction recording failed. Please try again.");
                }
            } else {
                conn.rollback();
                response.getWriter().println("Deposit failed. Please try again.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            response.getWriter().println("An error occurred: " + e.getMessage());
        } finally {
            try {
                if (updateStmt != null) updateStmt.close();
                if (transactionStmt != null) transactionStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("An error occurred while closing resources: " + e.getMessage());
            }
        }
    }
}
