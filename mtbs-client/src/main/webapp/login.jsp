<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    isELIgnored = "false"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Log In!</title>
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
 <form action = "/login" method = "POST" autocomplete="off">
  <h2>Login </h2>
  	<h3>
  		${SPRING_SECURITY_LAST_EXCEPTION.message}
	</h3>
   	<label>Username:</label> <input type = "text" name = "username" required/><br><br>
 	<label>Password:</label> <input type = "password" name = "password" id = "password" required/><br><br>
 	<button type="submit" id="submit">Login</button><br><br>
 </form>
 
<script>

</script>
</html>