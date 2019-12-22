
<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Sign Up!</title>
<style>
	form{
		text-align: center;
	}
	
	label{
	 display: inline-block;
	 font-size: 1.5em;
	 width: 10em;
	 text-align: left;
	}
	
	input{
	width: 15em;
	font-size: 1.3em;
	height: 2em;
	
	}
	
	button{
	width: 10em;
	height: 2.5em;
	font-size: 1.1em;
	}
	.login{
		text-align: center;
		margin-left: -11em;
	}
	
	.login span{
	 display: inline-block;
	 font-size: 1.5em;
	 text-align: left;
	 margin-right: 1em;
	}
	
	.form span{
	display: none;
	}
</style>
</head>
<body>

 <h1>Welcome to movie booking system</h1>
 <form action = "/register" method = "POST" autocomplete = "off" onsubmit = "return validateform()">
   <h2>${UserAlreadyExistsMessage}</h2>
  <h2>Sign up</h2>
   	<label>Username:</label> <input type = "text" name = "username" required/><br><br>
 	<label>Email:</label> <input type = "email" name = "email" required/><br><br>
 	<label>Password:</label> <input type = "password" name = "password" id = "password" required/><br><br>
 	<label>Confirm password:</label> <input type = "password" id = "confirm-password" required/><br><br>
 	<button type="submit" id="submit">Sign Up</button><br><br>
 </form>
 
 <div class ="login"> 
<span>Already a user? </span><a href = "/login"><button>Login</button></a>
</div>
</body>
<script>
 function validateform(){
	 let password = document.getElementById('password').value;
	 let confirmPassword = document.getElementById('confirm-password').value;
	 if(password !== confirmPassword){
		 alert('Passwords don\'t match');
		 return false;
	 }
	 else{
		 if(document.getElementsByName('username')[0].value.indexOf(' ') >= 0){
			 alert('Spaces not allowed in username');
			 return false;
		 }
		 else if(!validateEmail(document.getElementsByName('email')[0].value)){
			 alert('Not a valid email');
			 return false;
		 }
		 else if(!passwordMatchesRegex(password)){
				 alert('Password must have minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character');
			 return false;
		 }
 			 return true;
	 }
 }
 function passwordMatchesRegex(userPassword){
	 let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&#%^*]{8,}$/
	 return regex.test(userPassword);
 }
 function validateEmail(email) {
	    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(String(email).toLowerCase());
	}
</script>
</html>