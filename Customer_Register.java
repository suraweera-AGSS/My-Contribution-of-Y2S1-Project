package cus_reg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Customer_Payment
 */
@MultipartConfig
@WebServlet("/Customer_Register")
public class Customer_Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Customer_Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("full_name");
        String userName = request.getParameter("user_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("tel_num");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DBUtil.getConnection();
            String query = "insert into register (fullName, userName, email, phone, password, confirm_password) values(?,?,?,?,?,?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, userName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            stmt.setString(6, confirm_password);
            int row = stmt.executeUpdate();

            if (row > 0) {
                System.out.println("SignUp details added into database successfully...");

                // Set user information in request attributes or session
                request.setAttribute("fullName", fullName);
                request.setAttribute("userName", userName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);

                // Forward to customer profile page
                RequestDispatcher dispatcher = request.getRequestDispatcher("/customer_profile.jsp");
                dispatcher.forward(request, response);
            } else {
                System.out.println("Failed to add SignUp details into Database...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            DBUtil.closeResources(connection, stmt);
        }
    }

}
