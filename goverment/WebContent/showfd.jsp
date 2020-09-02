<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
          <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import=" java.sql.DriverManager" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.io.File" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
response.setContentType("application/pdf");
int o=Integer.parseInt(request.getParameter("o"));
Connection c=DriverManager.getConnection("jdbc:mysql://localhost/sworddatabase", "root", "");


String sql="select request.finished_document_location from request where request.status='finished' and request.id="+o;
Statement stmt=c.createStatement();
ResultSet rs=stmt.executeQuery(sql);
String path=null;
while(rs.next()){
path=rs.getString(1);
} 
FileInputStream fi=new FileInputStream(path);
int i;
while((i=fi.read())!=-1){
	out.write(i);
}
fi.close();
%>


</body>
</html>