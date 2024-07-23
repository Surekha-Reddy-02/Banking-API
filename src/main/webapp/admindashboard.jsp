<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f2f2f2;
        }
        .dashboard-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .customer-actions {
            text-align: center;
            margin-top: 30px;
        }
        .customer-actions a {
            text-decoration: none;
            color: #007BFF;
            font-size: 18px;
            margin-right: 15px;
            display: inline-block;
            margin-bottom: 10px;
        }
        .customer-actions a:hover {
            text-decoration: underline;
        }
        .logout-button {
            display: block;
            width: 200px;
            padding: 10px;
            margin: 20px auto;
            background-color: #dc3545;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }
        .logout-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <%
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            response.sendRedirect("adminlogin.jsp");
            return;
        }
    %>
    <div class="dashboard-container">
        <h2>Admin Dashboard</h2>
        
        <h3>Customer Management</h3>
        <div class="customer-actions">
            <a href="registercustomer.jsp">Register Customer</a>
            <a href="viewcustomers.jsp">View Customers</a>
            <a href="modifycustomers.jsp">Modify Customer</a>
            <a href="deletecustomer.jsp">Delete Customer</a>
        </div>
        <form action="adminlogin.jsp" method="post">
            <input type="hidden" name="logout" value="true">
            <button type="submit" class="logout-button">Logout</button>
        </form>
        <% 
            String logout = request.getParameter("logout");
            if ("true".equals(logout)) {
                session.invalidate();
                response.sendRedirect("adminlogin.jsp");
            }
        %>
    </div>
</body>
</html>