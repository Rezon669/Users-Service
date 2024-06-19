<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>reset password</title>
<link rel="stylesheet" type="text/css"
	href="/static/css/resetpassword.css" />
</head>
<body>
	<h2>Please reset your password</h2>
	<div class="emailverify">
		<form method="post" action="/easybuy/user/updatepassword">


			<p>${errorMessage}</p>
			
			<label>Email Id</label><br> <input type="text" name="emailid"
				id="emailid" placeholder="Enter your Email Id"><br> <br>

			<label>Password</label><br> <input type="text" name="password"
				id="password" placeholder="Enter Password"><br>
			<br> <label>Confirm Password</label><br> <input
				type="password" name="confirmpassword" id="confirmpassword"
				placeholder="Confirm Password"><br>
			<br> <input id="submit" type="submit" value="Submit"><br>
			<br>
		</form>
		
		<t></t><a href="/easybuy/user/signin"> Click here</a><t> to login if  you remember the password</t>
	</div>
</body>
</html>