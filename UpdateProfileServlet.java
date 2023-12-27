package cus_reg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("full_name");
        String userName = request.getParameter("user_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("tel_num");

        Connection connection = null;

        String url = "jdbc:mysql://localhost:3306/trains";
        String Username = "root";
        String Password = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, Username, Password);
            PreparedStatement stmt;
            String query = "UPDATE register SET fullName=?, userName=?, email=?, phone=? WHERE user_id=?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, userName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            int userId = 0;
			stmt.setInt(5, userId);

            int row = stmt.executeUpdate();

            if (row > 0) {
                response.sendRedirect("customer_profile.jsp");
            } else {
                // error
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
