<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>create-account</title>
<link rel="stylesheet" type="text/css" href="/static/css/signup.css" />
</head>
<body>
	<h2>Enter your details to Create an Account</h2>
	<div class="signup">
		<form method="post" action="/easybuy/user/createaccount">


			<p>${errorMessage}</p>

			<label>Username</label><br> <input type="text" name="username"
				id="username" placeholder="Enter your Username"><br>
			<br> <label>Email ID</label><br> <input type="text"
				name="emailid" id="emailid" placeholder="Enter your Email Id"><br>
			<br> <label>Mobile Number</label><br> <input type="text"
				name="mobilenumber" id="mobilenumber"
				placeholder="Enter your Mobile Number"><br>
			<br> <label>Password</label><br> <input type="password"
				name="password" id="password" placeholder="Enter the Password"><br>
			<br> <label>City</label><br> <input type="text" name="city"
				id="city" placeholder="Enter the City"><br>
			
			<br> <label>Role</label><br> <select name="role"
				id="role">
				<option value="select one">select one</option>
				<option value="ADMIN">ADMIN</option>
				<option value="PUBLIC">PUBLIC</option>
			</select><br>
			<br> <input id="submit" type="submit" value="Save"><br><br>
			


		</form>

		<t></t><a href="/easybuy/user/signin"> Click here</a><t> to login if you have an account</t>
	</div>

</body>
</html>