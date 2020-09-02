

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
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
		String  fname=request.getParameter("frname");
		String fathername=request.getParameter("mrname");
		String lastname=request.getParameter("lrname");
		String mother=request.getParameter("mothername");
		int r=Integer.parseInt(request.getParameter("rn"));
		jdbc q=new jdbc();
		Connection con=q.connect();
		String sql="select * from citizen where firstname='"+fname+"' and father_name='"+fathername+"' and lastname='"+lastname+"' and mother_name='"+mother+"' and  record_number="+r+"";
		ResultSet rs=q.query(con, sql);
		request.setAttribute("table", rs);
       // PrintWriter pr=response.getWriter();
       // response.setContentType("text/html");
		
		
  RequestDispatcher rq=request.getRequestDispatcher("sr.jsp");
  rq.forward(request, response);
       
	}

}
