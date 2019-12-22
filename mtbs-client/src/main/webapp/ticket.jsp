<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    import = "java.time.LocalDateTime,java.time.format.DateTimeFormatter "
    isELIgnored = "false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Your Ticket</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<style>
span{
font-size: 1.3em;
}
pre{
	font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
	display: inline;
	font-size: 100%;
}
</style>
</head>
<body>
	<nav class = "navbar navbar-expand-lg navbar-dark bg-dark">
   <h1 class = "nav-item text-light">Welcome to BookMyMovie.com </h1>
   <a href = "logout" class = "navbar-brand" style = "margin-left: 59em;"> <button class = "btn btn-light btn-lg"> Logout </button></a>
   </nav>
   <div class = "mx-auto" style = "width:1100px; margin-top: 4%;">
   <h2>Here are your ticket details, ${username}<br>
   ----------------------------------------</h2>
   <span class = "font-weight-bold">Booking Id: </span><span>${ticket.id}</span><br><br>
   <span class = "font-weight-bold">Movie Name : </span><span>${ticket.showDetails.movie.name}</span><br><br>
   <span class = "font-weight-bold">Theater Name: </span><span>${ticket.showDetails.theater.name}</span><br><br>
   <span class = "font-weight-bold">City: </span><span>${ticket.showDetails.theater.location.cityName}</span><br><br>
   <span class = "font-weight-bold">Number of Tickets: </span><span>${ticketCount}</span> <br><br>
   <span class = "font-weight-bold">Seats:</span><span><pre>${seats}</pre></span><br><br>
   <span class = "font-weight-bold">Show timings: </span><span>${ticket.showDetails.localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy 'at' hh:mm a"))}</span><br><br>
   <span class = "font-weight-bold">Total Cost(including 18% GST): Rs ${String.format("%.2f", ticket.amountPaid)}</span><br><br>
   
   <span class = "font-weight-bold">An email regarding the same is sent to your email. Thank you for using bookmymovie. Have a great day!</span><br><br>
   <a href = "/selectLocation"><button class = "btn btn-primary btn-lg" style = "width: 250px;">Book another ticket</button></a><br><br>
 </div>
</body>
</html>