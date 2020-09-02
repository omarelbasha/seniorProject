

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class criminale
 */
@WebServlet("/criminale")
public class criminale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public criminale() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String father=request.getParameter("father");
		String lname=request.getParameter("lname");
		int  rn=Integer.parseInt(request.getParameter("rn"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date d=new Date(System.currentTimeMillis());
		
		jdbc a=new jdbc();
		Connection c=a.connect();
		String s="select id from citizen where firstname='"+fname+"' and record_number="+rn+" and lastname='"+lname+"' and  father_name='"+father+"'";
		ResultSet r=a.query(c, s);
		int id=0;
		try {
			while(r.next()) {
			 id=r.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int aid=Integer.parseInt(request.getParameter("cid"));
		String p="select count(*) from request where citizen_id="+aid+" and STATUS='processing' and request.type_id=4 and request.dcid="+id;
		int count=0;
		ResultSet u=a.query(c, p);
		try {
			while(u.next()) {
				count=u.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql=null;
		if(count==0) {
		 sql="insert into request(type_id,citizen_id,dcid,request_date,status)"
				+"values(4,"+aid+","+id+",'"+formatter.format(d)+"','processing')";
		}
		if(count==1) {
			sql="update request set dcid="+id+",updated_at='"+formatter.format(d)+"',comments='null'  where citizen_id="+aid+" and type_id=4";
		}
		a.insert(c, sql);
		
		response.sendRedirect("iresult.jsp");
		
		
		
	}

}
