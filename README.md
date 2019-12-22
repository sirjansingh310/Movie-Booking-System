# Movie Ticket Booking System

*Movie ticket booking system is created using 2 spring boot applications.*

A RESTful web service is created which runs on port 8081. 
The client web service running on port 8080, consumes the rest services and displays the JSP pages to the user's browser using ModelAndView. 
Both the applications exchange data with each other in JSON format.

Unit testing, for all the layers in both the applications is done, along with test cases for different HTTP response codes produced by the server.

Exception handling is also done for both the applications. If a particular exception occurs in either client or server application, the user is redirected to appropriate error web pages.

Concepts applied: Spring boot, rest, spring data, rest template(for the client), mockito, MockMvc, RestAssured. 
