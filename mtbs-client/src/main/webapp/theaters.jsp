<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    isELIgnored = "false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Select Theater</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<nav class = "navbar navbar-expand-lg navbar-dark bg-dark">
   <h1 class = "nav-item text-light">Welcome to BookMyMovie.com </h1>
   <a href = "logout" class = "navbar-brand" style = "margin-left: 59em;"> <button class = "btn btn-light btn-lg"> Logout </button></a>
   </nav>

   <form action = "showDetails" class = "mx-auto" style = "width:400px; margin-top: 15%;" method = "POST">
     <h3> Here are the theaters showing ${param.userMovie} </h3><br><br>
  
   <c:forEach items = "${theaters}" var = "theater">
     <div class="custom-control custom-radio">
    <input type="radio" class="custom-control-input" id="${theater.id}" name="userTheaterId" value = "${theater.id}" required>
    <label class="custom-control-label" for="${theater.id}">${theater.name}</label>
  </div>
    <br>
   </c:forEach>
      <button class = "btn btn-primary btn-lg" style = "width: 150px;"type = "submit">Next</button>
   
   </form>
</body>
</html>