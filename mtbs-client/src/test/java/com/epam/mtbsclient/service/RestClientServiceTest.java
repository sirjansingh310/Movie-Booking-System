package com.epam.mtbsclient.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest
class RestClientServiceTest {
	@Value("${api.url}")
	private String baseUri;
	
	@Test
	void getAllLocationsTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/location");
		assertEquals(200, response.getStatusCode());
	}

	@Test
	void getAllMoviesInLocationTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/movie/hyderabad");
		assertEquals(200, response.getStatusCode());
	}

	@Test
	void getAllTheatersByLocationAndMovieTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/theater/hyderabad/joker");
		assertEquals(200, response.getStatusCode());
	}

	@Test
	void getShowDetailsListTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/showdetails/joker?theaterId=1");
		assertEquals(200, response.getStatusCode());
	}

	@Test
	void getSeatLayoutTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/booking/1");
		assertEquals(200, response.getStatusCode());
	}
	@Test
	void getMoviesInWrongLocationTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/movie/new%20york");
		assertEquals(204, response.getStatusCode());
	}
	@Test
	void getUserTest() {
		RestAssured.baseURI = baseUri;
		RequestSpecification requestSpecification = RestAssured.given();
		Response response = requestSpecification.get("/user/aaa");
		assertEquals(200, response.getStatusCode());
	}
}
