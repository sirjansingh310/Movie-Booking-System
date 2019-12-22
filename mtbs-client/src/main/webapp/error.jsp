<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    isELIgnored = "false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="ISO-8859-1">
<title>Error!</title>
</head>
<body>
<h2>The server encountered an error and could not complete your request. Please try again later</h2> 
  ${param.errorMessage}
</body>
</html>