<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    isELIgnored = "false"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Verify your account</title>
<style>
	form{
		text-align: center;
	}
	
	label{
	 display: inline-block;
	 font-size: 1.5em;
	 width: 5em;
	 text-align: left;
	}
	
	input{
	width: 10em;
	font-size: 1.3em;
	height: 2em;
	
	}
	
	button{
	width: 12em;
	height: 2.5em;
	margin-left: 7em;
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
	#resend, #resend:visited{
	display: block;
	text-decoration: none;
	color: #6c757d!important;
	text-align: center;
	margin-left: 7em;
	}
</style>
</head>
<body>

 <h1>Welcome to movie booking system</h1>
  
 <form action = "/verify" method = "POST" autocomplete="off">
 <h3 style = "color: #dc3545;">${invalidOTPMessage}</h3>
  <h2 style = "margin-left: 2.5em;">A verification OTP is sent to your email. Enter the OTP to confirm your registration.</h2>
   	<label>OTP:</label> <input type = "text" name = "OTP" required/><br><br>
 	<button type="submit" id="submit">Verify</button><br><br>
 </form>
 <a href = "/resendOTP" id = "resend">Didn't receive the OTP? Click here to resend the OTP</a>
</html>