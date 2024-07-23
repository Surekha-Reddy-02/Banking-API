<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Type</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .type-selector-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }
        .type-selector-container h2 {
            margin-bottom: 20px;
        }
        .type-selector-container button {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            background-color: #007BFF;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .type-selector-container button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="type-selector-container">
        <h2>Select User Type</h2>
        <!-- Redirect to adminLogin.jsp when Admin button is clicked -->
        <button onclick="window.location.href='admin.jsp'">Admin</button>
        <!-- Redirect to customerLogin.jsp when Customer button is clicked -->
        <button onclick="window.location.href='customerLogin.jsp'">Customer</button>
    </div>
</body>
</html>
