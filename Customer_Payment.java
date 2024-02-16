package cus_pay;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cus_reg.DBUtil;

@WebServlet("/Customer_Payment")
@MultipartConfig
public class Customer_Payment extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String holderName = request.getParameter("cardHolder");
        String paymentMethod = request.getParameter("payment_method");
        String cardNumber = request.getParameter("cardNumber");
        String payDate = request.getParameter("payDate");
        String amount = request.getParameter("amount");
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PreparedStatement stmt;
            String query = "INSERT INTO payment (holderName, paymentMethod, cardNumber, payDate, amount) VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, holderName);
            stmt.setString(2, paymentMethod);
            stmt.setString(3, cardNumber);
            stmt.setString(4, payDate);
            stmt.setString(5, amount);
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Payment details added into database successfully...");
                response.sendRedirect("payment_details.jsp?cardHolder=" + holderName +
                        "&payment_method=" + paymentMethod +
                        "&cardNumber=" + cardNumber +
                        "&payDate=" + payDate +
                        "&amount=" + amount);
            } else {
                System.out.println("Failed to add payment details into Database...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection);
        }
    }
}
