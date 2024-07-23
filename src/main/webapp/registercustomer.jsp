<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f2f2f2;
        }
        .registration-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-group input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            cursor: pointer;
        }
        .form-group input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="registration-container">
        <h2>Register a New Customer</h2>
        <form action="registercustomerprocess.jsp" method="post">
            <div class="form-group">
                <label for="full_name">Full Name</label>
                <input type="text" id="full_name" name="full_name" required>
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" required>
            </div>
            <div class="form-group">
                <label for="mobile_no">Mobile No</label>
                <input type="text" id="mobile_no" name="mobile_no" required>
            </div>
            <div class="form-group">
                <label for="email_id">Email ID</label>
                <input type="email" id="email_id" name="email_id" required>
            </div>
            <div class="form-group">
                <label for="account_type">Account_type</label>
                <select id="account_type" name="account_type" required>
                    <option value="Saving">Saving Account</option>
                    <option value="Current">Current Account</option>
                </select>
            </div>
            <div class="form-group">
                <label for="initial_balance">Initial Balance (min 1000)</label>
                <input type="number" id="initial_balance" name="initial_balance" min="1000" required>
            </div>
            <div class="form-group">
                <label for="date_of_birth">Date of Birth</label>
                <input type="date" id="date_of_birth" name="date_of_birth" required>
            </div>
            <div class="form-group">
                <label for="id_proof">ID Proof</label>
                <input type="text" id="id_proof" name="id_proof" required>
            </div>     
            <div class="form-group">
                <input type="submit" value="Register Customer">
            </div>
        </form>
    </div>
</body>
</html>
