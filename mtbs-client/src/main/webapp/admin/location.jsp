<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Manage Locations</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

</head>
<body>
<nav class = "navbar navbar-expand-lg navbar-dark bg-dark">
   <h1 class = "nav-item text-light">BookMyMovie Admin Portal </h1>
   <a href = "/logout" class = "navbar-brand" style = "margin-left: 59em;"> <button class = "btn btn-light btn-lg"> Logout </button></a>
   </nav>
   	<h4 class = "mx-auto" style = "width:40%; margin-top: 10%;">Manage Locations: </h4>
   	<div  class = "mx-auto" style = "width:40%;">
	<table class = "table table-bordered table-hover">
	<thead class = "thead-dark">
	<tr>
	<th scope = "col">id</th>
	<th scope = "col">City</th>
	<th></th>
	<th></th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var = "location" items = "${allLocations}">
	<tr id = "${location.id}">
	<td>${location.id}</td>
	<td>${location.cityName}</td>
	<td><button class = "btn btn-warning" onclick = "handleEdit(this, ${location.id})">EDIT</button>
		<button class = "btn btn-success" id = "save-${location.id}" style = "display:none;" >SAVE</button>
	</td>
	<td><a href = "/admin/deleteLocation?locationId=${location.id}"><button class = "btn btn-danger">DELETE</button></a></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
	<div class = "mx-auto" style = "width:40%; margin-top: 2%;">
		<form action = "/admin/addLocation" method = "post">
		<div class = "form-group">
		<input type = "text" class = "form-control" name = "cityName" placeholder = "Enter a new city name to add a location" required/>
		</div>
		<button type = "submit" class = "btn btn-success">Add new Location</button>
		</form>
	</div>
	<script type = "text/javascript">
	function handleEdit(currentEditButton, locationId){
		let tr = document.getElementById(locationId);
		let tdArray = tr.getElementsByTagName('td');
		tdArray[1].contentEditable = true;
		tdArray[1].focus();
		currentEditButton.style.display = 'none';
		let saveButton = document.getElementById('save-' + locationId);
		saveButton.style.display = 'block';
		saveButton.addEventListener('click', function() {
			window.location.href = "/admin/editLocation?locationId=" + locationId + "&cityName=" + tdArray[1].innerHTML;
		})
		
	}
	</script>
</body>
</html>
