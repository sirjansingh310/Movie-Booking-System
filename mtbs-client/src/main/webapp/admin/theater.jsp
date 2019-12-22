<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Manage Theaters</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<h1 class="nav-item text-light">BookMyMovie Admin Portal</h1>
		<a href="/logout" class="navbar-brand" style="margin-left: 59em;">
			<button class="btn btn-light btn-lg">Logout</button>
		</a>
	</nav>
	<h4 class="mx-auto" style="width: 40%; margin-top: 10%;">Manage
		Theaters:</h4>
	<div class="mx-auto" style="width: 40%;">
		<table class="table table-bordered table-hover">
			<thead class="thead-dark">
				<tr>
					<th scope="col">id</th>
					<th scope="col">Name</th>
					<th scope="col">Location</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="theater" items="${allTheaters}">
					<tr>
						<td>${theater.id}</td>
						<td>${theater.name}</td>
						<td>${theater.location.cityName}</td>
						<td><a href=""><button class="btn btn-warning">EDIT</button></a></td>
						<td><a href="/admin/deleteTheater?theaterId=${theater.id}"><button
									class="btn btn-danger">DELETE</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="mx-auto" style="width: 40%; margin-top: 2%;">
		<form action="/admin/addTheater" method="post">
			<div class="form-group">
				<input type="text" class="form-control" name="name"
					placeholder="Enter a new theater name " required/>
			</div>
			<div class="form-group">
				<label for="selectTheater">Select Location</label>
				 <select
					class="form-control" id="selectLocation" name = "locationId">
					<c:forEach var = "location" items = "${allLocations}">
					 <option value = "${location.id}">${location.cityName} </option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" class="btn btn-success">Add new
				theater</button>
		</form>
	</div>
</body>
</html>
