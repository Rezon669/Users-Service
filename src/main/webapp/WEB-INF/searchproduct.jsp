<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>search product</title>
<link rel="stylesheet" type="text/css"
	href="static/css/searchproduct.css" />
</head>
<body>
	<form id="search" method="get" action="/easybuy/product/public/searchproducts">
<p>${errorMessage}</p>
		<label>Search Keyword</label><br>
		<br> <input type="text" name="searchkeyword" id="searchkeyword"
			autofocus><br>
		<br> <input type="submit" id="search" value="Search"><br><br>
		
		<t>	<a href="/easybuy/user/backtohome">Click here</a> to go back</t>
	</form>
</body>
</html>