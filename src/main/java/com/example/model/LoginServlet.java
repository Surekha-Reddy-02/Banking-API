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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection details
    private static final String URL = "jdbc:mysql://localhost:3306/gen";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Suryareddy@020";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL query to check customer credentials
            String sql = "SELECT * FROM customers WHERE account_no = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNo);
            pstmt.setString(2, password);

            // Execute query
            rs = pstmt.executeQuery();

            // Check if credentials are correct
            if (rs.next()) {
                // Retrieve or create session
                HttpSession session = request.getSession(true);
                
                // Set session attributes
                session.setAttribute("account_no", accountNo);
                session.setAttribute("initial_balance", rs.getDouble("initial_balance"));

                // Redirect to customer dashboard
                response.sendRedirect("customerdashboard.jsp");
            } else {
                // Redirect back to login page with error message
                response.sendRedirect("customerLogin.jsp?error=Invalid account number or password.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("customerLogin.jsp?error=An error occurred. Please try again.");
        } finally {
            // Close database connections
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
