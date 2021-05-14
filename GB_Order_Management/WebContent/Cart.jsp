<%@page import= "com.Cart" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Cart.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

<h1>Cart Management </h1>
<form id="formCart" name="formCart" method="post" action="Cart.jsp">
 Product Name: 
<input id="ptname" name="ptname" type="text" 
 class="form-control form-control-sm">
<br> Unit Price: 
<input id="unitPrice" name="unitPrice" type="text" 
 class="form-control form-control-sm">
<br> Quantity: 
<input id="qty" name="qty" type="text" 
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
<input type="hidden" id="hidCartIDSave" name="hidCartIDSave" value="">
</form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>


<br>
<div id="divCartsGrid">

<%
 Cart cartObj = new Cart(); 
 out.print(cartObj.readCarts()); 
%>
</div>

</div></div></div>

</body>
</html>