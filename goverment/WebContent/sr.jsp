<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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


<%if(request.getAttribute("table")!=null){
	ResultSet w=(ResultSet)request.getAttribute("table");
String a=(String)session.getAttribute("c");
if(a.equals("criminal") ){
	
	

	

%>
<script type="text/javascript">
function f(a){
	window.location.replace("criminal.jsp?e="+a);
}
</script>
<%} %>
<%if(a.equals("id")){ %>
<script type="text/javascript">
function f(a){
	window.location.replace("id.jsp?e="+a);
}
</script>
<%} %>
<% if(a.equals("birth") ){
%>
<script type="text/javascript">
function f(a){
	window.location.replace("birth.jsp?e="+a);
}
</script>
<%} %>
<% if(a.equals("death") ){
%>
<script type="text/javascript">
function f(a){
	window.location.replace("death.jsp?e="+a);
}
</script>
<%} %>
<% if(a.equals("current") ){
%>
<script type="text/javascript">
function f(a){
	window.location.replace("current.jsp?e="+a);
}
</script>
<%} %>
<table class="table table-hover" border="1" >
  <thead>
    <tr>
     
      <th scope="col">First Name</th>
      <th scope="col">Father Name</th>
      <th scope="col">Last Name</th>
	   <th scope="col">Mother Name</th>
	    <th scope="col">Date of Birth</th>
		 <th scope="col">Address of  Birth</th>
	    <th scope="col">Record Number</th>
		 <th scope="col">Blood Type</th>
		  
   </tr>
  </thead>
  <tbody>
  <%while(w.next()){ %>
  <tr onclick="f(<%=w.getInt(1)%>)"><td><%=w.getString(2)%></td>
  <td><%=w.getString(3) %></td>
  <td><%=w.getString(4) %></td>
  <td><%=w.getString(5) %></td>
    <td><%=w.getString(9) %></td>
      <td><%=w.getString(10) %></td>
        <td><%=w.getString(8) %></td>
          <td><%=w.getString(12) %></td>
          </tr><% }%>
          </tbody>
          </table>
<%} %>
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

</body>
</html> 