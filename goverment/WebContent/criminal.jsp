<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <title>Criminal Record Request</title>
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
<%if(session.getAttribute("em")==null ){
	RequestDispatcher d=request.getRequestDispatcher("login.jsp");
} 

%>

<% 
if(request.getQueryString()==null){
	response.sendRedirect("g.jsp");
}
else{
int a=Integer.parseInt(request.getParameter("e"));
Connection c=DriverManager.getConnection("jdbc:mysql://localhost/sworddatabase", "root", "");
String sql="select * from citizen where id="+a;
Statement s=c.createStatement();
ResultSet r=s.executeQuery(sql);
String fname=null;
String father=null;
String lname=null;
int rn=0;
while(r.next()){
	fname=r.getString(2);
	father=r.getString(3);
	lname=r.getString(4);
	rn=r.getInt(8);
}
c.close();

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
<div class="container">
  <h2>Criminal Record Request</h2>
  <form class="form-horizontal" method="post" action="criminale" >
  <input type="hidden" name="cid" value=<%=a %>>
    <div class="form-group">
      <label class="control-label col-sm-2" for="fname">First Name:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="fname" placeholder="Enter first name" name="fname" value=<%=fname %>   required>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="mname">Father Name :</label>
      <div class="col-sm-10">          
        <input type="text" class="form-control" id="mname" placeholder="Enter Father Name" name="father" value=<%=father %> required>
      </div>
    </div>
     <div class="form-group">
      <label class="control-label col-sm-2" for="lname">Last Name :</label>
      <div class="col-sm-10">          
        <input type="text" class="form-control" id="lname" placeholder="Enter Last Name" name="lname" value=<%=lname %> required>
      </div>
    </div>
	 <div class="form-group">
      <label class="control-label col-sm-2" for="rn">Record number :</label>
      <div class="col-sm-10">          
        <input type="number" class="form-control" id="rn" placeholder="Enter Record number" name="rn" value=<%=rn %> required>
      </div>
    </div>
	        
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">Submit</button>
		<input type="reset" name="btnreset" value="cancel" class="btn btn-default">
      </div>
    </div>
	
  </form>
</div>
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