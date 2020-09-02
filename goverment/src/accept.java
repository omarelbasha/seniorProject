

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import goverment.createpdf;

/**
 * Servlet implementation class accept
 */
@WebServlet("/accept")
public class accept extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accept() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int o=Integer.parseInt(request.getParameter("o"));
		jdbc a=new jdbc();
		Connection c=a.connect();
		String sql="select citizen.id,citizen.firstname,citizen.father_name,citizen.lastname,citizen.mother_name,citizen.record_number,citizen.blood_type,citizen.partner_fullname,citizen.religion from citizen,request where citizen.id=request.dcid and request.id="+o;
		ResultSet r=a.query(c, sql);
		String sonname=null;
		String fname=null;
		String father=null;
		String lname=null;
		String mname=null;
		int rn=0;
		String btype=null;
		int citizenid=0;
		String dateb=null;
		String dated=null;
		String partner=null;
		String son="select sonname,dateofbirth,dateofdeath from request where id="+o;
		String rel=null;
		try {
			
			while(r.next()){
				citizenid=r.getInt(1);
				fname=r.getString(2);
				father=r.getString(3);
				lname=r.getString(4);
				mname=r.getString(5);
				rn=r.getInt(6);
				btype=r.getString(7);
				partner=r.getString(8);
				rel=r.getString(9);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		String tsql="select request_type.type from request_type,request where request_type.id=request.type_id and request.id="+o;
		ResultSet u=a.query(c, tsql);
		String type=null;
		try {
			while(u.next()) {
				type=u.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String path="D://pdf//"+fname+"_"+type+".pdf";
	
		Document d=new Document();
		try {
			PdfWriter.getInstance(d, new FileOutputStream(path));
			d.open();
			Paragraph g2=new Paragraph(type);
			g2.setAlignment(Element.ALIGN_CENTER);
			Paragraph  g = null;
			String a1[]= new String[25];
			String b[]=new String[25];
			String po=null;
			int i=0;
			int j;
			if(type.equals("Criminal certificate")) {
				String scriminal="select criminal_recor.criminal_name,criminal_recor.date_of_criminal from criminal_recor where criminal_recor.citizenid="+citizenid;
				ResultSet cu=a.query(c, scriminal);
				
				while(cu.next()) {
					a1[i]=cu.getString(1);
					b[i]=cu.getDate(2).toString();
					i=i+1;
				}
				for(j=0;j<i;j++) {
					po=po+"Criminal Name:"+a1[j]+" criminal date:"+b[j]+"\n";
				}
				g=new Paragraph(po);
				
			}
			else {
				if(type.equals("Birth certificate")) {
					ResultSet sonres=a.query(c, son);
					try {
						while(sonres.next()) {
							sonname=sonres.getString(1);
							dateb=sonres.getDate(2).toString();
							
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					g=new Paragraph("First Name:"+sonname+"\nFather Name:"+fname+""
				+ "\n Last Name:"+lname+""
						+ "\nMother Name:"+partner
						
								+ "\nRecord Number"+rn+""
										+ "\nDate of Birth:"+dateb);
				}else {
					if(type.equals("death certificate")) {
						ResultSet sonres=a.query(c, son);
						try {
							while(sonres.next()) {
								
								dated=sonres.getDate(3).toString();
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						g=new Paragraph("First Name:"+fname+"\nFather Name:"+father+""
								+ "\n Last Name:"+lname+""
										+ "\nMother Name:"+mname+""
												+ "\nRecord Number"+rn+""
														+ "\nBlood Type:"+btype+""
																+ "\nDate of Death"+dated);
					}
					else {
		  g=new Paragraph("First Name:"+fname+"\nFather Name:"+father+""
				+ "\n Last Name:"+lname+""
						+ "\nMother Name:"+mname+""
								+ "\nRecord Number"+rn+""
										+ "\nBlood Type:"+btype);}}}
				d.add(g2);
			d.add(g)	;
			
				d.close();
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(type);
String sl="update request set finished_document_filename='"+fname+".pdf',finished_document_location='"+path+"',status='finished' where id="+o;
	a.insert(c, sl);	
	String sinbd=null;
	if(type.equals("Birth certificate")) {
		sinbd="insert into citizen(firstname,father_name,lastname,mother_name,record_number,dob,status,religion) values('"+sonname+"','"+fname+"','"+lname+"','"+partner+"',"+rn+",'"+dateb+"','Single','"+rel+"')";
		a.insert(c, sinbd);
	}
	if(type.equals("death certificate")) {
		sinbd="update citizen set dateofdeath='"+dated+"' where id="+citizenid;
	a.insert(c, sinbd);
	}
		response.sendRedirect("process.jsp");
		

		
		

}
}
