package com.example.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithdrawalServlet")
public class WithdrawalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String accountNo = (String) session.getAttribute("account_no");
        String withdrawalAmountStr = request.getParameter("withdrawal_amount");

        if (accountNo == null || withdrawalAmountStr == null || withdrawalAmountStr.trim().isEmpty()) {
            response.sendRedirect("withdrawal.jsp?error=Invalid account number or withdrawal amount.");
            return;
        }

        double withdrawalAmount = 0.0;
        try {
            withdrawalAmount = Double.parseDouble(withdrawalAmountStr.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("withdrawal.jsp?error=Invalid withdrawal amount.");
            return;
        }

        Connection conn = null;
        PreparedStatement updateStmt = null;
        PreparedStatement transactionStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "Suryareddy@020");

            // Begin transaction
            conn.setAutoCommit(false);

            // Check current balance
            String selectQuery = "SELECT initial_balance FROM customers WHERE account_no = ?";
            selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, accountNo);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("initial_balance");

                if (withdrawalAmount > currentBalance) {
                    response.sendRedirect("withdrawal.jsp?error=Insufficient funds.");
                    return;
                }

                // Update balance
                String updateQuery = "UPDATE customers SET initial_balance = initial_balance - ? WHERE account_no = ?";
                updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDouble(1, withdrawalAmount);
                updateStmt.setString(2, accountNo);
                int rowCount = updateStmt.executeUpdate();

                if (rowCount > 0) {
                    // Record transaction
                    String insertQuery = "INSERT INTO transactions (account_no, description, amount, balance, transaction_date) VALUES (?, ?, ?, ?, ?)";
                    transactionStmt = conn.prepareStatement(insertQuery);
                    transactionStmt.setString(1, accountNo);
                    transactionStmt.setString(2, "Withdrawal");
                    transactionStmt.setDouble(3, withdrawalAmount);
                    transactionStmt.setDouble(4, currentBalance - withdrawalAmount);
                    transactionStmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

                    transactionStmt.executeUpdate();

                    // Commit transaction
                    conn.commit();

                    // Update session attribute with new balance
                    session.setAttribute("initial_balance", currentBalance - withdrawalAmount);

                    // Redirect to success page
                    response.sendRedirect("customerdashboard.jsp?withdrawalSuccess=true");
                } else {
                    // Rollback transaction
                    conn.rollback();
                    response.sendRedirect("withdrawal.jsp?error=Withdrawal failed. Please try again.");
                }
            } else {
                response.sendRedirect("withdrawal.jsp?error=Account not found.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            response.sendRedirect("withdrawal.jsp?error=An error occurred. Please try again.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (selectStmt != null) selectStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (transactionStmt != null) transactionStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("withdrawal.jsp?error=An error occurred while closing resources.");
            }
        }
    }
}
