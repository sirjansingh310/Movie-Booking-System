<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Book Seats</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<style>
span {
	display: inline-block;
	width: 25px;
	margin-left: 2px;
}
</style>
<script>
window.history.forward();
function noBack() {
    window.history.forward();
}
</script>
</head>
<body>
	<nav class = "navbar navbar-expand-lg navbar-dark bg-dark">
   <h1 class = "nav-item text-light">Welcome to BookMyMovie.com </h1>
   <a href = "logout" class = "navbar-brand" style = "margin-left: 59em;"> <button class = "btn btn-light btn-lg"> Logout </button></a>
   </nav>
	<br>
	<br>
	<h3 class = "mx-auto" style = "width: 400px;">---Screen This Way---</h3>
	
	<form action="printTicket" method="post" class = "mx-auto" style = "width:1000px; margin-top: 2%; padding-left: 13.5%;">

		<%
			char rowChar = 'A';
		%>
		<c:forEach var="seatType" items="${seatLayout.seatTypeList}">
			<h4>${seatType.category} at Rs
				${seatType.price}
			per ticket
			</h4>
			<input type = "hidden" name = "price-${seatType.id}" value = "${seatType.price }" />
			<c:forEach begin="1" end="${seatType.numberOfRows}"
				varStatus="rowCount">
				<c:forEach begin="1" end="${seatType.numberOfColumns}"
					varStatus="colCount">
					<c:set var="rowChar" value="<%=rowChar%>" scope="page" />
					<c:set var="seat" value="${rowChar}${colCount.index}" scope="page" />
					<c:if test="${fn:contains(seatLayout.bookedSeats, seat)}">
						<input type="checkbox" name="seats"
							value="${seat}-${seatType.id}" disabled />
						<span>${seat}</span>

					</c:if>
					<c:if test="${!fn:contains(seatLayout.bookedSeats, seat)}">
						<input type="checkbox" name="seats"
							value="${seat}-${seatType.id}" />
						<span>${seat}</span>

					</c:if>
				</c:forEach>
				<br>
				<%
					rowChar++;
				%>
			</c:forEach>
			<br>
			<br>
		</c:forEach>


		<br>
		   <button id = "submit" class = "btn btn-primary btn-lg" style = "width: 150px;"type = "submit" disabled>Book Seats</button>
		<br><br>
		<h6>Please select at least one and not more than 5 seats to proceed.</h6>
	</form>
	<div class = "font-weight-bold float-right" style ="margin-right: 5%;">
	<p>Ticket Count: </p><span id = "ticketCount">0</span>
	<p>Total Cost(in Rs.): </p><span id = "totalCost" >0</span>
	</div>
	<script>
	window.addEventListener('load', () => {
		let count =  0;
		let checkboxes = document.querySelectorAll('input');
		checkboxes.forEach((checkbox) => {
			checkbox.addEventListener('change', () => {
				if(checkbox.checked){
					count++;
					let seat = checkbox.value;
					let seatId = seat.split('-')[1];
					let price = parseInt(document.getElementsByName('price-' + seatId)[0].value);
					let prevTotalCost = parseInt(document.getElementById('totalCost').innerHTML);
					document.getElementById('totalCost').innerHTML = prevTotalCost + price;
				}
				else{
					count--;
					let seat = checkbox.value;
					let seatId = seat.split('-')[1];
					let price = parseInt(document.getElementsByName('price-' + seatId)[0].value);
					let prevTotalCost = parseInt(document.getElementById('totalCost').innerHTML);
					document.getElementById('totalCost').innerHTML = prevTotalCost - price;
				}
				let ticketCount = document.getElementById('ticketCount');
				ticketCount.innerHTML = count;
				if(count > 5){
					document.getElementById('submit').disabled = true;
				}
				else if(count >= 1 && count <=5){
					document.getElementById('submit').disabled = false;
				}
				else {
					document.getElementById('submit').disabled = true;
				}
			})
		})
		document.getElementsByTagName('form')[0].addEventListener('submit' , () => {
			let totalCostWithGST = parseInt(document.getElementById('totalCost').innerHTML);
			totalCostWithGST *= 118;
			totalCostWithGST /= 100;
			let msg = 'The total cost including GST is Rs ' + 
			totalCostWithGST+
			' Are you sure you want to proceed with the booking?';
			return confirm(msg);
			return false;
		})
	})



</script>
</body>

</html>
