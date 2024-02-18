package cus_reg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteProfileServlet")
public class DeleteProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DBUtil.getConnection();
            String query = "DELETE FROM register WHERE userName=?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, userName);

            int row = stmt.executeUpdate();

            if (row > 0) {
                System.out.println("Profile deleted successfully...");
                response.sendRedirect("login.jsp"); // Redirect to login page
            } else {
                System.out.println("Failed to delete profile...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResources(connection, stmt);
        }
    }
}
