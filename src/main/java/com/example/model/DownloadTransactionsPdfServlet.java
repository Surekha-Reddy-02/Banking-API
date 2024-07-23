package com.example.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.example.web.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/downloadTransactionsPdf")
public class DownloadTransactionsPdfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Transaction> transactions = getLast10Transactions();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Last 10 Transactions"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            addRows(table, transactions);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

    private List<Transaction> getLast10Transactions() {
        List<Transaction> transactions = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/gen";
        String user = "root";
        String password = "Suryareddy@020";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions ORDER BY transaction_date DESC LIMIT 10")) {

            while (rs.next()) {
                Transaction txn = new Transaction();
                txn.setId(rs.getInt("id"));
                txn.setAccountNo(rs.getLong("account_no"));
                txn.setDescription(rs.getString("description"));
                txn.setAmount(rs.getDouble("amount"));
                txn.setBalance(rs.getDouble("balance"));
                txn.setTransactionDate(rs.getTimestamp("transaction_date"));
                transactions.add(txn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase("Customer ID"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Account No"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Description"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Balance"));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Date"));
        table.addCell(cell);
    }

    private void addRows(PdfPTable table, List<Transaction> transactions) {
        for (Transaction txn : transactions) {
            table.addCell(String.valueOf(txn.getId()));
            table.addCell(String.valueOf(txn.getAccountNo()));
            table.addCell(txn.getDescription());
            table.addCell(String.valueOf(txn.getAmount()));
            table.addCell(String.valueOf(txn.getBalance()));
            table.addCell(txn.getTransactionDate().toString());
        }
    }
}
