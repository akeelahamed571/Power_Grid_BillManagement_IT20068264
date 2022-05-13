<%@page import="model.samplebill"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Bill.js"></script>
</head>
<body> 
<div class="container"><div class="row"><div class="col-6"> 
<h1>Bill Management </h1>
<form id="formItem" name="formItem">
 category: 
 <input id="category" name="category" type="text" 
 class="form-control form-control-sm">
 <br>
 Account No: 
 <input id="acno" name="acno" type="text" 
 class="form-control form-control-sm">
 <br> year: 
 <input id="year" name="year" type="number" 
 class="form-control form-control-sm">
  <br> month: 
 <input id="month" name="month" type="number" 
 class="form-control form-control-sm">
 <br> Due: 
 <input id="due" name="due" type="text" 
 class="form-control form-control-sm">
 <br> No of units: 
 <input id="noofunits" name="noofunits" type="text" 
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidItemIDSave" 
 name="hidItemIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divItemsGrid">
 <%
 samplebill itemObj = new samplebill(); 
 out.print(itemObj.ReadBill()); 
 %>
</div>
</div> </div> </div> 
</body>
</html>
