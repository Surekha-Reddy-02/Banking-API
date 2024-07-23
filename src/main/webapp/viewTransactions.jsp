<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Customers</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f2f2f2;
        }
        .customer-list {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <div class="customer-list">
        <h2>View Transactions</h2>
        
        <table>
            <thead>
                <tr>
                    <th>Customer ID</th>
                    <th>Account_No</th>
                    <th>Description</th>
                    <th>Amount</th>
                    <th>Balance</th>
                    <th>Transaction_Date</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    Connection conn = null;
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gen", "root", "Suryareddy@020");
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM transactions";
                        rs = stmt.executeQuery(sql);
                        
                        while(rs.next()) {
                %>
                            <tr>
                                <td><%= rs.getInt("id") %></td>
                                <td><%= rs.getString("account_no") %></td>
                                <td><%= rs.getString("description") %></td>
                                <td><%= rs.getString("amount") %></td>
                                <td><%= rs.getString("balance") %></td>
                                <td><%= rs.getString("transaction_date") %></td>
                            </tr>
                <% 
                        }
                    } catch (SQLException e) {
                        out.println("SQL Exception: " + e.getMessage());
                    } catch (ClassNotFoundException e) {
                        out.println("Class Not Found Exception: " + e.getMessage());
                    } finally {
                        // Close resources in reverse order
                        try {
                            if (rs != null) rs.close();
                            if (stmt != null) stmt.close();
                            if (conn != null) conn.close();
                        } catch (SQLException e) {
                            out.println("Error closing database resources: " + e.getMessage());
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
    <form action="downloadTransactionsPdf" method="post">
        <button type="submit">Download Last 10 Transactions as PDF</button>
    </form>
</body>
</html>
