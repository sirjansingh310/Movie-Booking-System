<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Manage Movies</title>
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
<style>
#toggleButton.collapsed:before {
	content: 'View Locations';
}

#toggleButton:before {
	content: 'Hide Locations';
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<h1 class="nav-item text-light">BookMyMovie Admin Portal</h1>
		<a href="/logout" class="navbar-brand" style="margin-left: 59em;">
			<button class="btn btn-light btn-lg">Logout</button>
		</a>
	</nav>
	<h4 class="mx-auto" style="width: 40%; margin-top: 10%;">Manage
		Movies:</h4>
	<div class="mx-auto" style="width: 40%;">
		<table class="table table-bordered table-hover">
			<thead class="thead-dark">
				<tr>
					<th scope="col">id</th>
					<th scope="col">Movie</th>
					<th scope="col">Locations</th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="movie" items="${allMovies}">
					<tr>
						<td>${movie.id}</td>
						<td>${movie.name}</td>
						<td><button id="toggleButton" type="button"
								class="btn btn-primary collapsed" data-toggle="collapse"
								data-target="#locationsForMovie${movie.id}"></button>
							<div id="locationsForMovie${movie.id}" class="collapse">
								<c:forEach var="location" items="${movie.locationList}">
						${location.cityName}
						<br>
								</c:forEach>
							</div></td>
						<td>
							<button type="button" class="btn btn-warning" data-toggle="modal"
								data-target="#editModal" data-movieid="${movie.id}" data-moviename = "${movie.name}">EDIT</button>
						</td>
						<td><a href="/admin/deleteMovie?movieId=${movie.id}"><button
									class="btn btn-danger">DELETE</button></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="mx-auto" style="width: 40%; margin-top: 2%;">
		<form action="/admin/addMovie" method="post">
			<div class="form-group">
				<input type="text" class="form-control" name="name"
					placeholder="Enter a new movie name" />
			</div>
			<h6>Choose Location(s) for this movie:</h6>
			<c:forEach var="location" items="${allLocations}">
				<div class="custom-control custom-checkbox">
					<input type="checkbox" class="custom-control-input"
						id="${location.id}" name="locationId" value="${location.id}"
						 /> <label class="custom-control-label"
						for="${location.id}">${location.cityName}</label> <br>
				</div>
			</c:forEach>
			<br>
			<button type="submit" class="btn btn-success">Add new Movie</button>
		</form>
	</div>
	<!-- Modal for edit -->

	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Edit Movie</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id = "editForm" action="/admin/editMovie" method="post">
						<div class="form-group">
							<label for="movieId" class="col-form-label">Movie Id:</label> <input
								type="text" class="form-control" id="movieIdForEdit" name="movieId"
								readonly />
						</div>
						<div class="form-group">
							<input type="text" class="form-control" name="name" id = "movieNameForEdit"/>
						</div>
						<h6>Choose Location(s) for this movie:</h6>
						<c:forEach var="location" items="${allLocations}">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input"
									id="${location.id}EDIT" name="locationId" value="${location.id}"
									required /> <label class="custom-control-label"
									for="${location.id}EDIT">${location.cityName}</label> <br>
							</div>
						</c:forEach>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" onclick = "submitForm()">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#editModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var movieId = button.data('movieid') // Extract info from data-* attributes
			var movieName = button.data('moviename')
			var modal = $(this)
			modal.find('#movieIdForEdit').val(movieId)
			modal.find('#movieNameForEdit').val(movieName)
		})
		function submitForm(){
			document.getElementById('editForm').submit();
		}
	</script>
</body>
</html>
