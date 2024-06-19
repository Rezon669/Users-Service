<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="static/css/app.css" />
<title>Welcome</title>
</head>

<body>
	<h1>Welcome...!</h1>
	<div class="home">
		<form id="home" method="get" action="#">
			<label for="home"><b>Select any one option:</b></label><br>
			<br>

			<div class="button-container-div">
				<input type="button"
					onclick="window.location.href='/easybuy/secure/addproduct';"
					value="Add Product" />

			</div>
			<br>
			<br>
			<div class="button-container-div">
				<input type="button"
					onclick="window.location.href='/easybuy/secure/listofproducts';"
					value="List of Products" />

			</div>
			<br>
			<br>
			<div class="button-container-div">
				<input type="button"
					onclick="window.location.href='/easybuy/secure/searchproduct';"
					value="Search Product" /> <br>
				<br>
			</div>


		</form>
	</div>

</body>
</html>
