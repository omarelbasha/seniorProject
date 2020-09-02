

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
 * Servlet implementation class death
 */
@WebServlet("/death")
@MultipartConfig(location="", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*100, maxRequestSize=1024*1024*5*5)
public class death extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public death() {
        super();
        // TODO Auto-generated constructor stub
    }


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String father=request.getParameter("mname");
		String mname=request.getParameter("mothername");
		int day=Integer.parseInt(request.getParameter("day"));
		int month=Integer.parseInt(request.getParameter("dob-month"));
		int year=Integer.parseInt(request.getParameter("dob-year"));
		String date=year+"/"+month+"/"+day;
		
		int rn=Integer.parseInt(request.getParameter("rn"));
		
		jdbc g=new jdbc();
		String s="select id from request_type where type='death certificate'";
		String u="select id from citizen where firstname='"+fname+"' and lastname='"+lname+"'and father_name='"+father+"' and record_number="+rn;
		
		Connection c=g.connect();
		ResultSet rs=g.query(c, s);
		int q=Integer.parseInt(request.getParameter("cid"));
		int a = 0;
		int o=0;
		try {
			while(rs.next()) {
				
					a=rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet re=g.query(c, u);
		try {
			while(re.next()) {
				o=re.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count=0;
		String sql=null;
		String sq2=null;
		String p="select count(*) from request where citizen_id="+q+" and STATUS='processing' and request.type_id=3 and request.dcid="+o;
		ResultSet ut=g.query(c, p);
		try {
			while(ut.next()) {
				count=ut.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count);
		if(count==0) {
		sql="Insert into request(type_id,citizen_id,dcid,request_date,status,dateofdeath) values("+a+","+q+","+o+",'"+java.time.LocalDate.now()+"','processing','"+date+"')";
		
		
	sq2="insert into request_document(request_id,documnent) values(?,?)";
		}
		if(count==1) {
			
		sql="update request set dcid="+o+",updated_at='"+java.time.LocalDate.now()+"',dateofdeath='"+date+"' where citizen_id="+q+" and type_id=3";
		
		}
	String sq1="select max(id) from request";
g.insert(c,sql);
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
	sq2="update request_document set request_id=?,documnent=? where request_id="+w;}
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
