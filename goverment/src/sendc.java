

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sendc
 */
@WebServlet("/sendc")
public class sendc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String s=request.getParameter("comm");
		jdbc a=new jdbc();
		Connection c=a.connect();
		 int o=Integer.parseInt(request.getParameter("o"));
		String sql="update request set comments='"+s+"' where id="+o;
		a.insert(c, sql);
		response.sendRedirect("iresult.jsp");
		
		
	}

}
