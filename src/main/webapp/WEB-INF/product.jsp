<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/static/css/product.css" />
<title>additem</title>

</head>
<body>
	<h2>Enter your Product details</h2>
	<div class="addproduct">
		<form method="post" action="/easybuy/product/secure/addproduct">
<p>${errorMessage}</p>
			<label>Product name</label><br> <input type="text" name="productname"
				id="name"><br>
			<br> <label>Price</label><br> <input type="text"
				name="price" id="price"><br>
			<br> <label>Quantity</label><br> <input type="text"
				name="quantity" id="quantity"><br>
			<br> <label>Category</label><br> <select name="category"
				id="category">
				<option value="select one">select one</option>
				<option value="grocery">Grocery</option>
				<option value="Mobiles">Mobiles</option>
				<option value="Fashion">Fashion</option>
				<option value="Electronics">Electronics</option>
				<option value="Home">Home</option>
				<option value="Furniture">Furniture</option>
				<option value="Beauty">Beauty</option>
				<option value="Sports">Sports</option>
			</select><br>
			<br> <label>Search Keyword</label><br> <input type="text"
				name="searchkeyword" id="searchkeyword"><br>
			<br> <input id="submit" type="submit" value="Save"> <br><br>
			
		<t>	<a href="/easybuy/user/backtohome">Click here</a> to go back</t>
		</form>
	</div>



  
    
   <!-- 
    const token = localStorage.getItem('accessToken');
console.log('Tokeninside:1', token);
    fetch('/easybuy/product/addproducts', {
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


<script>
//window.addEventListener('DOMContentLoaded', (event) => {
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

</script>-->
</body>
</html>