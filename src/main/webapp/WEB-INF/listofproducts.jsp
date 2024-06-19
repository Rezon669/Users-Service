<%@page import="com.ecom.cartservice.model.Product"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="static/css/listofitems.css" />
<title>listofproducts</title>
</head>
<body>
	<h2>List of Products</h2>

	<table border="2">
		<th>Name</th>
		<th>Price</th>
		<th>Quantity</th>
		<th>Category</th>
		<th>Action</th>
		<%
		List<Product> s = (List<Product>) request.getAttribute("list");
		for (int c = 0; c < s.size(); c++) {
			out.print("<tr> ");
			out.print("<td>" + s.get(c).getProductname() + "</td>");
			out.print("<td>" + s.get(c).getPrice() + "</td>");
			out.print("<td>" + s.get(c).getQuantity() + "</td>");
			out.print("<td>" + s.get(c).getCategory() + "</td>");
		    out.print("<td>");
		%>
		    <form action="/easybuy/cart/public/addtocart" method="post">
                <input type="hidden" name="productId" value="<%= s.get(c).getProductid() %>">
                <input type="submit" value="Add to Cart">
            </form>
		<%
		    out.print("</td>");
			out.print(" </tr>");
		}
		%>
	</table>
	<h4>
		<t><a href="/easybuy/user/backtohome">click here</a> to go back</t>
	</h4>
</body>
</html>
