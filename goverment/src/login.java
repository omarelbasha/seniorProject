
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("email");
	String pass=request.getParameter("password");
	HttpSession session=request.getSession();
jdbc f=new jdbc();
Connection con=f.connect();
String sql="select count(*) from users where email='"+email+"' and Password=md5('"+pass+"')  ";
ResultSet rs=f.query(con, sql);


 try {
	while(rs.next()) {
		
		int  a=rs.getInt(1);
		
		if(a==1) {
			String q="select verifiyed from users where email='"+email+"'";
			ResultSet rq=f.query(con, q);
			while(rq.next()) {
				int d=rq.getInt(1);
				if(d==0) {
					response.sendRedirect("login.jsp");
				}
			}
			
			 session.setAttribute("em", email);
			 if(request.getParameterValues("rember")!=null) {
				Cookie e=new Cookie("email", email);
				e.setMaxAge(60*60*24);
				Cookie g=new Cookie("password", pass);
				g.setMaxAge(60*60*24);
				response.addCookie(e);
				response.addCookie(g);
			 }
			 String o="select role from users where email='"+email+"'";
				ResultSet ro=f.query(con, o);
				while(ro.next()) {
					String role=ro.getString(1);
					if(role.equals("admin")) {
						response.sendRedirect("admin.jsp");
					}
				}
			if(request.getAttribute("mess")!=null) {
				request.removeAttribute("mess");
			}
			 response.sendRedirect("g.jsp");}
		else {
			request.setAttribute("mess", "invlaid email and password");
			RequestDispatcher r=request.getRequestDispatcher("login.jsp");
			r.forward(request, response);
		}
		
		
	 }
	f.disconnect(con);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 catch (Exception e) {
	e.printStackTrace();
}
	}

}
