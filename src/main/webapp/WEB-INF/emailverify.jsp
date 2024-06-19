<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>email verify</title>
<link rel="stylesheet" type="text/css"
	href="/static/css/emailverify.css" />
</head>
<body>
	<br>
	<br>
	<div class="emailverify">
		<form method="get" action="/easybuy/user/emailidverification">

			<t>Enter your Email id to reset password</t>

			<p>${errorMessage}</p>
			<label>Email id</label><br> <input type="text" name="emailid"
				id="emailid" placeholder="Enter your Email Id"><br> <br>
			<input id="submit" type="submit" value="Submit"><br> <br>


<t></t><a href="/easybuy/user/signin"> Click here</a><t> to login if you remember your password</t>
		</form>
	</div>
</body>
</html>