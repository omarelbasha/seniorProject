

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class Birth
 */
@WebServlet("/Birth")
@MultipartConfig(location="", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*100, maxRequestSize=1024*1024*5*5)
public class Birth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Birth() {
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
		String day=request.getParameter("day");
		String month=request.getParameter("dob-month");
		String year=request.getParameter("dob-year");
		String date=year+"/"+month+"/"+day;
		System.out.println(date);
		jdbc g=new jdbc();
		String s="select id from request_type where type='Birth certificate'";
		Connection c=g.connect();
		ResultSet rs=g.query(c, s);
		int q=Integer.parseInt(request.getParameter("cid"));
		int a = 0;
		try {
			while(rs.next()) {
				
					a=rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql=null;
		String sq2=null;
		int count=0;
	 
		
		String p="select count(*) from request where citizen_id="+q+" and STATUS='processing' and request.type_id=2 and request.dcid="+q;
	ResultSet ut=g.query(c, p);
	try {
		while(ut.next()) {
			count=ut.getInt(1);
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.out.println(count);
	if(count==0) {
		sql="Insert into request(type_id,citizen_id,dcid,request_date,status,dateofbirth,sonname) values("+a+","+q+","+q+",'"+java.time.LocalDate.now()+"','processing','"+date+"','"+fname+"')";
		sq2="insert into request_document(request_id,documnent) values(?,?)";
	}
	if(count==1) {
		sql="update request set dcid="+q+",updated_at='"+java.time.LocalDate.now()+"',comments='null',dateofbirth='"+date+"',sonname='"+fname+"' where citizen_id="+q+" and type_id=2";}
	
	g.insert(c,sql);	
String sq1="select max(id) from request";

	int w=0;
	ResultSet rw=g.query(c, sq1);
	try {
		while(rw.next()) {
			w=rw.getInt(1);
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	if(count==1) {
		sq2="update request_document set request_id=?,documnent=? where request_id="+w;
	}
	//System.out.println(w);
	InputStream inputStream = null;
	 Part filePart = request.getPart("image");
	 
	  if (filePart != null) {
          
      
            
           // obtains input stream of the upload file
           inputStream = filePart.getInputStream();
       }
	PreparedStatement statement;
	try {
		statement = (PreparedStatement) c.prepareStatement(sq2);
		statement.setInt(1, w);
		statement.setBlob(2, inputStream);
	int t=statement.executeUpdate();
	PrintWriter out=response.getWriter();
	response.setContentType("text/Html");
if(t>0) {
	response.sendRedirect("iresult.jsp");
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}
