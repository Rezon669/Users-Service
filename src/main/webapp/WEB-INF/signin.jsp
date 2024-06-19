<%@ page language="java" contentType="text/html charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>signin</title>
<link rel="stylesheet" type="text/css" href="/static/css/signin.css" />
</head>
<body>
	<h2>Enter your details to login</h2>
	<div class="signin">

		<form method="post" action="/easybuy/user/authenticate">

			<p>${errorMessage}</p>
			<label>EmailId</label><br> <input type="text" name="emailid"
				id="emailid" placeholder="Enter your Emailid"><br>
			<br> <label>Password</label><br> <input type="password"
				name="password" id="password" placeholder="Enter your Password"><br>
			<br> <input id="submit" type="submit" value="Sign in"><br>
			<br>

		</form>
		<t>Click here to <a href="/easybuy/user/signup">Create an Account</a>
		<t>
		<br>
		<br>
		<t>
		<a href="/easybuy/user/resetpassword">Forgot Password</a>
		<t>
	</div>

<!-- 
<script>// Assuming loginData contains username and password
fetch('/easybuy/user/loginvalidation', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(Login)
})
.then(response => response.json())
.then(data => {
    const token = data.token;
    // Store the token securely in local storage
    localStorage.setItem('accessToken', token);
    // Redirect or perform actions based on successful login
})
.catch(error => {
    // Handle login error
    const error = ${errorMessage};
    console.error('Login failed:', error);
});
</script>
 
<script>
    document.getElementById("loginForm").addEventListener("submit", function(event) {
        event.preventDefault();
        let formData = new FormData(this);

        fetch("/easybuy/user/loginvalidation", {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorMessage) {
                // Handle invalid credentials error
                alert(data.errorMessage);
            } else {
                // Store the received token securely (e.g., in localStorage)
                localStorage.setItem("token", data.token);

                // Perform a GET request to the received view name
                fetch(`/${data.viewName}`, {
                    method: 'GET'
                })
                .then(response => response.text())
                .then(html => {
                    document.write(html); // Replace the current page with the fetched HTML
                })
                .catch(error => {
                    console.error('Error fetching page:', error);
                    // Handle error fetching the page
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle other errors here
        });
    });
</script>
<script>
function loginUser() {
    const emailid = document.getElementById('emailid').value;
    const password = document.getElementById('password').value;

    const loginData = {
        emailid: emailid,
        password: password
    };

    fetch('/easybuy/user/loginvalidation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Invalid Login Credentials');
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('accessToken', data.access)
        localStorage.setItem('refresh', data.refresh)
        console.log(localStorage)

        // Redirect to the welcome page or perform actions based on the response
        window.location.href = '/welcome'; // Replace with your welcome page URL
    })
    .catch(error => {
        console.error('Error:', error);
        // Handle login error here, show error message to the user, etc.
    });
}
</script>-->
</body>
</html>