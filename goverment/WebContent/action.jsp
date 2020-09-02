<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import=" java.sql.DriverManager" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body {
  font-family: "Lato", sans-serif;
}

/* Fixed sidenav, full height */
.sidenav {
  height: 100%;
  width: 200px;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #111;
  overflow-x: hidden;
  padding-top: 20px;
}

/* Style the sidenav links and the dropdown button */
.sidenav a, .dropdown-btn {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 20px;
  color: #818181;
  display: block;
  border: none;
  background: none;
  width: 100%;
  text-align: left;
  cursor: pointer;
  outline: none;
}

/* On mouse-over */
.sidenav a:hover, .dropdown-btn:hover {
  color: #f1f1f1;
}

/* Main content */
.main {
  margin-left: 200px; /* Same as the width of the sidenav */
  font-size: 20px; /* Increased text to enable scrolling */
  padding: 0px 10px;
}

/* Add an active class to the active dropdown button */
.active {
  background-color: green;
  color: white;
}

/* Dropdown container (hidden by default). Optional: add a lighter background color and some left padding to change the design of the dropdown content */
.dropdown-container {
  display: none;
  background-color: #262626;
  padding-left: 8px;
}

/* Optional: Style the caret down icon */
.fa-caret-down {
  float: right;
  padding-right: 8px;
}

/* Some media queries for responsiveness */
@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}
</style>
</head>
<body>

<div class="sidenav">
  


	<a href="user.jsp">User</a>
	<a href="process.jsp">process</a>
	
	


  
</div>

<div class="main">
<%if(session.getAttribute("em")==null){
	RequestDispatcher r=request.getRequestDispatcher("process.jsp");
} 
if(request.getQueryString()==null){
	response.sendRedirect("process.jsp");
}
else{
	int o=Integer.parseInt(request.getParameter("o"));
	Connection c=DriverManager.getConnection("jdbc:mysql://localhost/sworddatabase", "root", "");
	Statement stmt=c.createStatement();
	String sql="select request_type.type,request.dcid from request,request_type where request.type_id=request_type.id and request.id="+o;
	ResultSet rs=stmt.executeQuery(sql);
	String s=null;
	int d=0;
	while(rs.next()){
		s=rs.getString(1);
		d=rs.getInt(2);
	}
String se="select citizen.firstname,citizen.father_name,citizen.lastname,citizen.record_number  from citizen where id="+d;
%>
<p><%=s%></p>

<%
if(!s.equals("Birth certificate") && !s.equals("death certificate")){
	%>
	<table border="1">
<tr><td>First Name</td><td>Father Name</td><td>Last Name</td><td>Record Number</td><td>Action</td></tr>
<tr>
	<% 
Statement set=c.createStatement();
ResultSet rq=set.executeQuery(se);
while(rq.next()){
%>
<td><%=rq.getString(1) %></td><td><%=rq.getString(2) %></td><td><%=rq.getString(3) %></td><td><%=rq.getInt(4) %></td><td><a href="accept.jsp?o=<%=o%>">Accept</a><br><a href="sendc.jsp?o=<%=o%>">Send Comment</a></td>
<%} %>
</tr>
</table>
<%}
else{
	if(s.equals("death certificate")){
		%>
			<table border="1">
<tr><td>First Name</td><td>Father Name</td><td>Last Name</td><td>Date of Death</td><td>Record Number</td><td>Action</td></tr>
<tr>
	<%
	String cb="select citizen.firstname,citizen.father_name,citizen.lastname,request.dateofdeath,citizen.record_number from request,citizen where request.dcid=citizen.id and request.id="+o;
Statement sbe=c.createStatement();
ResultSet rqb=sbe.executeQuery(cb);
while(rqb.next()){
%>
<td><%=rqb.getString(1) %></td><td><%=rqb.getString(2) %></td><td><%=rqb.getString(3) %></td><td><%=rqb.getDate(4) %></td><td><%=rqb.getInt(5) %></td><td><a href="accept.jsp?o=<%=o%>">Accept</a><br><a href="sendc.jsp?o=<%=o%>">Send Comment</a></td>
<%} %>
</tr>
</table>
	<% }
	else{
%>
<table border="1">
<tr><td>First Name</td><td>Father Name</td><td>Last Name</td><td>Date of birth</td><td>record number</td><td>Action</td></tr>
<tr>
<%
String b="select request.sonname,citizen.firstname,citizen.lastname,request.dateofbirth,citizen.record_number from request,citizen where request.citizen_id=citizen.id and request.id="+o;
Statement sve=c.createStatement();
ResultSet rqs=sve.executeQuery(b);
while(rqs.next()){
%>
<td><%=rqs.getString(1) %></td><td><%=rqs.getString(2) %></td><td><%=rqs.getString(3) %></td><td><%=rqs.getDate(4) %></td><td><%=rqs.getString(5) %></td><td><a href="accept.jsp?o=<%=o%>">Accept</a><br><a href="sendc.jsp?o=<%=o%>">Send Comment</a></td>
<% } } }%>
</tr>
</table>
<%String ste="select count(*) from request_document where request_document.request_id="+o;
Statement mt=c.createStatement();
ResultSet mte=mt.executeQuery(ste);
while(mte.next()){
	if(mte.getInt(1)>0){
		%>
		<a href="showd.jsp?o=<%=o%>">show document</a>
		
	
	
	<%}
}
%>
</div>

<script>
/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
  this.classList.toggle("active");
  var dropdownContent = this.nextElementSibling;
  if (dropdownContent.style.display === "block") {
  dropdownContent.style.display = "none";
  } else {
  dropdownContent.style.display = "block";
  }
  });
}
</script>
<% }%>
</body>

</html> 