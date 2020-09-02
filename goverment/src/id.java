

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
 * Servlet implementation class id
 */
@WebServlet("/id")
@MultipartConfig(location="", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*100, maxRequestSize=1024*1024*5*5)
public class id extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public id() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String father=request.getParameter("mname");
		String mname=request.getParameter("mothername");
		int rn=Integer.parseInt(request.getParameter("rn"));
		jdbc g=new jdbc();
		String s="select id from request_type where type='Id card'";
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
		ResultSet ry=g.query(c, u);
		try {
			while(ry.next()) {
				o=ry.getInt(1);
			
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql=null;
		String sq2=null;
		int count=0;
		String p="select count(*) from request where citizen_id="+q+" and STATUS='processing' and request.type_id=1 and request.dcid="+q;
		ResultSet ut=g.query(c, p);
		try {
			while(ut.next()) {
				count=ut.getInt(1);
			}
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(count==0) {
		sql="Insert into request(type_id,citizen_id,dcid,request_date,status) values("+a+","+q+","+o+",'"+java.time.LocalDate.now()+"','processing')";
	 sq2="insert into request_document(request_id,documnent) values(?,?)";}
	if(count==1) {
		sql="update request set dcid="+o+",updated_at='"+java.time.LocalDate.now()+"',comments='null' where citizen_id="+q+" and type_id=1";
	}
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
		sq2="update request_document set request_id=?,documnent=? where request_id="+w;}
	
	//System.out.println(w);
	InputStream inputStream = null;
	InputStream in=null;
	 Part filePart = request.getPart("image");
	 Part fil=request.getPart("cr");
	  if (filePart != null && fil!=null) {
          
      
            
           // obtains input stream of the upload file
           inputStream = filePart.getInputStream();
           in=fil.getInputStream();
       }
	PreparedStatement statement;
	PreparedStatement st;
	try {
		statement = (PreparedStatement) c.prepareStatement(sq2);
		statement.setInt(1, w);
		statement.setBlob(2, inputStream);
	int t=statement.executeUpdate();
	st=(PreparedStatement)c.prepareStatement(sq2);
	statement.setInt(1, w);
	statement.setBlob(2, in);
	int te=statement.executeUpdate();
	PrintWriter out=response.getWriter();
	response.setContentType("text/Html");
if(t>0 && te>0) {
	response.sendRedirect("iresult.jsp");
	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
