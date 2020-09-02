<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page import="java.sql.Blob"%>
    <%@page import="java.io.InputStream"%>
    <%@page import="java.io.IOException"%>
    <%@page import="java.io.ByteArrayOutputStream"%>
    <%@page import ="java.sql.Connection"%>
<%@page import ="java.sql.DriverManager"%>
<%@page import ="java.sql.Statement"%>
<%@page import ="java.sql.ResultSet"%>
<%@page import ="java.sql.SQLException"%>
<%@page import ="java.util.Base64"%>
<%@page import="java.io.OutputStream" %>
<!DOCTYPE html>
<html>
<head>
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
<%
if(request.getAttribute("em")==null){
	RequestDispatcher r=request.getRequestDispatcher("login.jsp");
}
if(request.getQueryString()==null){
	response.sendRedirect("process.jsp");
}
else{
	int id=Integer.parseInt(request.getParameter("o"));
String databaseURL = "jdbc:mysql://localhost/sworddatabase";
String user = "root";
String password = "";
Connection connection = DriverManager.getConnection(databaseURL, user, password);
OutputStream o =null;
String sql="select * from request_document where request_id="+id;
Statement st=connection.createStatement();
ResultSet rs=st.executeQuery(sql);
Blob image=null;
byte[ ] imgData = null ;
rs.last();
image=rs.getBlob(3);
	imgData = image.getBytes(1,(int)image.length());

response.setContentType("application/pdf");


o = response.getOutputStream();

o.write(imgData);

o.flush();

o.close();
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
<%} %>
</body>
</html> 