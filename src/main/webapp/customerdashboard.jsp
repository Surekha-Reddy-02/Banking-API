<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 20px;
            color: #333;
        }

        h2 {
            color: #007bff;
            text-align: center;
        }

        h3 {
            color: #333;
            margin-top: 20px;
        }

        table {
            width: 60%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #ffffff;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        table th, table td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: left;
        }

        table th {
            background-color: #f7f7f7;
            color: #007bff;
        }

        ul {
            list-style-type: none;
            padding: 0;
            text-align: center;
        }

        ul li {
            margin: 15px 0;
        }

        a {
            text-decoration: none;
            color: #007bff;
            font-size: 18px;
        }

        a:hover {
            text-decoration: underline;
        }

        .logout-form {
            text-align: center;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>Welcome to Your Dashboard</h2>
    
    <%-- Display account details --%>
    <h3>Account Details</h3>
    <table>
        <tr>
            <th>Account Number:</th>
            <td>${sessionScope.account_no}</td>
        </tr>
        <tr>
            <th>Amount:</th>
            <td>
                <%
                    Double initialBalance = (Double) session.getAttribute("initial_balance");
                    if (initialBalance != null) {
                        out.print(String.format("%.2f", initialBalance));
                    } else {
                        out.print("N/A");
                    }
                %>
            </td>
        </tr>
        <!-- Other non-sensitive details can be displayed here -->
    </table>
    
    <%-- Links for actions --%>
    <h3>Actions</h3>
    <ul>
        <li><a href="viewTransactions.jsp">View Transactions</a></li>
        <li><a href="depositMoney.jsp">Deposit Money</a></li>
        <li><a href="Withdrawal.jsp">Withdraw Money</a></li>
    </ul>
    
    <div class="logout-form">
        <form action="LogoutServlet" method="post">
            <input type="submit" value="Logout">
        </form>
    </div>
</body>
</html>