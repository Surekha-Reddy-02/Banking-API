<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Withdrawal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 20px;
        }

        h2 {
            color: #007bff;
            text-align: center;
        }

        .form-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-size: 16px;
            color: #333;
        }

        input[type="text"],
        input[type="number"] {
            width: calc(100% - 22px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .message {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Withdrawal</h2>
        <form action="WithdrawalServlet" method="post">
            <label for="account_no">Account Number:</label>
            <input type="text" id="account_no" name="account_no" value="<%= session.getAttribute("account_no") %>" readonly><br><br>
            
            <label for="withdrawal_amount">Withdrawal Amount:</label>
            <input type="number" id="withdrawal_amount" name="withdrawal_amount" step="0.01" min="0" required><br><br>
            
            <input type="submit" value="Withdraw">
        </form>

        <%-- Display withdrawal success or error message --%>
        <div class="message">
            <%
                String withdrawalSuccess = request.getParameter("withdrawalSuccess");
                String error = request.getParameter("error");
                if (withdrawalSuccess != null && withdrawalSuccess.equals("true")) {
            %>
            <p style="color: green;">Withdrawal successful.</p>
            <%
                } else if (error != null && !error.isEmpty()) {
            %>
            <p style="color: red;"><%= error %></p>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>