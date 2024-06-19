<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>back-to-login</title>
<link rel="stylesheet" type="text/css"
	href="/static/css/backtologin.css" />
</head>
<body>
	<br>
	<br>
	<h2>
	Thank you...!
		<%=request.getParameter("username")%>
		Successfully created the account</h2>
		<h2>We have sent the Welcome email to the provided Email Id</h2>
		<br>
	<h2>
		<a href="/easybuy/user/signin">click here</a> to go back to login page
	</h2>
</body>
</html>
