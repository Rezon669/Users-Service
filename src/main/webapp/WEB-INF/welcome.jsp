<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>homepage</title>
<link rel="stylesheet" type="text/css" href="/static/css/welcome.css" />
</head>
<body>


	<h2>
		Welcome
		<%=request.getParameter("username")%></h2>
	<br>
	<form method="get" action="/easybuy/user/public/logout">
		<input id="submit" type="submit" value="Sign Out"><br> <br>
	</form>
	<div class="homepage">

		<label for="home"><b>Click on the provided options:</b></label> <br>
		<br>
		<br>
		<br>

		<form method="get" action="/easybuy/product/secure/addproducts">
			<input id="button" type="submit" value="Add Product"><br>
			<br>
			<br>
		</form>
		<form method="get" action="/easybuy/product/public/getproducts">
			<input id="button" type="submit" value="List of Products"><br>
			<br>
			<br>
		</form>
		<form method="get" action="/easybuy/product/public/searchproduct">
			<input id="button" type="submit" value="Search Product"><br>

		</form>


		<br> <br>


	</div>

<!-- 
<script>
var jwttoken =%>';
localStorage.setItem('accessToken', jwttoken);
localStorage.setItem('refreshToken', jwttoken);
console.log('Toke:',jwttoken);
const token = localStorage.getItem('accessToken');
//Log the token to the console
console.log('Token:', token);

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('accessToken');
    console.log('Token:', token);

    if (token) {
        console.log('Token inside:', token);

        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${token}`);

        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };

        fetch('/easybuy/product/addproducts', requestOptions)
            .then(response => {
                // Handle response data
            })
            .catch(error => {
                // Handle error
            });
    } else {
        console.log('Token not found');
        // Handle the case when the token is not available in localStorage
    }
});
</script>

// Check if the token exists
if (token) {
	console.log('Tokeninside:', token);
	 console.log('Request Headers:', {
	        'Authorization': `Bearer ${token}`
	    });
    // If the token exists, use it for subsequent API calls
    
    var myHeaders = new Headers();
myHeaders.append("Authorization", "Bearer ${token}");

var requestOptions = {
		  method: 'GET',
		  headers: myHeaders,
		  redirect: 'product'
		};
		
		fetch('/easybuy/product/addproducts', {
        method: 'GET',
        headers: {
        	 'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
            	

        }
   
    })
  
    .then(response => {
        // Handle response data
    })
    .catch(error => {
        // Handle error
    });
} else {
    // If the token is not found or null
    console.log('Token not found');
}

fetch('/easybuy/product/listofproducts', {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${token}`
    }
})
.then(response => {
    // Handle response data
})
.catch(error => {
    // Handle error
});

fetch('/easybuy/product/searchitem', {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${token}`
    }
})
.then(response => {
    // Handle response data
})
.catch(error => {
    // Handle error
});

</script> 

<script>
//Get the token from localStorage

//localStorage.setItem('accessToken', jwttoken);
//console.log('Toke:',jwttoken);
//const token = localStorage.getItem('accessToken');
//Log the token to the console
//console.log('Token:', token);
var jwttoken ='';
console.log('token', jwttoken);
localStorage.setItem('accessToken', jwttoken);
const token = localStorage.getItem('accessToken');
console.log('Token:', token);
if (token) {
  var myHeaders = new Headers();
  myHeaders.append("Authorization", 'Bearer ${token}');

  var requestOptions = {
    method: 'GET',
    headers: myHeaders,
    redirect: 'product'
  };

  fetch("/easybuy/product/addproducts", requestOptions)
    .then(response => response.text())
    .then(result => console.log(result))
    .catch(error => console.log('error', error));
} else {
  console.log('Token not found in localStorage');
  // Handle the case where the token is not available in localStorage
</script>-->
</body>
</html>