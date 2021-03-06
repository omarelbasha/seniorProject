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
<title>Current</title>
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
<%if(session.getAttribute("em")==null){
	RequestDispatcher d=request.getRequestDispatcher("login.jsp");
}

if(request.getQueryString()==null){
	response.sendRedirect("g.jsp");
}
else{
%>
<div class="sidenav">
  
  <button class="dropdown-btn">New Request 
    <i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">

	<a href="search.jsp?p=1">Id Card Request</a>
	<a href="search.jsp?p=2">birth Request</a>
	<a href="search.jsp?p=3">Death Request</a>
	<a href="search.jsp?p=4">Criminal  Record Request</a>
  </div>
  <a href="search.jsp?p=5">Current Request</a>
  
</div>

<div class="main">

<table border="1">
<tr><td>type id</td><td>request by</td><td>request for</td><td>Comment</td><td>Edit</td></tr>
<% int a=Integer.parseInt(request.getParameter("e"));

Connection c=DriverManager.getConnection("jdbc:mysql://localhost/sworddatabase", "root", "");


String sql="select request_type.type,citizen.firstname,citizen.lastname,request.dcid,request.comments from request,citizen,request_type where request.type_id=request_type.id and request.citizen_id= citizen.id and request.status='processing' and citizen.id="+a; 
Statement stmt=c.createStatement();
ResultSet rs=stmt.executeQuery(sql);
String url=null;
while(rs.next()){
	
%>

<tr><td><%=rs.getString(1) %></td><td><%=rs.getString(2) %> <%=rs.getString(3) %></td><td><a href="s.jsp?o=<%=rs.getInt(4) %>"><%=rs.getInt(4)%></a></td><td><%=rs.getString(5) %></td>
<%
if(rs.getString(1).equals("Criminal certificate")){
	url="criminal";
}
if(rs.getString(1).equals("Id card")){
	url="id";
}
if(rs.getString(1).equals("Birth certificate")){
	url="birth";
}
if(rs.getString(1).equals("death certificate")){
	url="death";
}
%>
<td><a href="<%=url %>.jsp?e=<%=a %>">edit</a>
<% 
} %>
</table>
<a href="showfinish.jsp?o=<%=a%>" id="finish">show Finnished process</a>
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