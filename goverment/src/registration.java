

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String pass=request.getParameter("password");
		String cpass=request.getParameter("confirm_password");  
		
		if(pass.equals(cpass)) {
			jdbc f=new jdbc();
			Connection c=f.connect();
			String sql="INSERT INTO users(FirstName,LastName,phone_number,email,Password,role,verifiyed) values('"+fname+"','"+lname+"','"+phone+"','"+email+"',md5('"+pass+"'),'user',0)";
			f.insert(c, sql);
		response.sendRedirect("result.jsp");
		}
		else {
			
response.sendRedirect("reg.jsp");

		       
		}
	
	}

}
