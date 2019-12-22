package com.epam.mtbsclient.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.epam.mtbsclient.dto.LocationDTO;
import com.epam.mtbsclient.dto.MovieDTO;
import com.epam.mtbsclient.dto.SeatLayoutDTO;
import com.epam.mtbsclient.dto.SeatTypeDTO;
import com.epam.mtbsclient.dto.ShowDetailsDTO;
import com.epam.mtbsclient.dto.TheaterDTO;
import com.epam.mtbsclient.dto.TicketDTO;
import com.epam.mtbsclient.dto.UserDTO;

@Service
public class RestClientService {
	private RestTemplate restTemplate;

	public RestClientService() {
		restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				// override default behavior of throwing error by resttemplate when http code is
				// 400-500
			}
		});
	}

	@Value("${api.url}")
	private String apiUrl;

	public ResponseEntity<List<LocationDTO>> getAllLocations() {
		return restTemplate.exchange(apiUrl.concat("/location"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LocationDTO>>() {
				});
	}

	public ResponseEntity<List<MovieDTO>> getAllMoviesInLocation(String location) {
		return restTemplate.exchange(apiUrl.concat("/movie/").concat(location), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MovieDTO>>() {
				});
	}

	public ResponseEntity<List<TheaterDTO>> getTheatersList(String userLocation, String userMovie) {
		return restTemplate.exchange(apiUrl.concat("/theater/").concat(userLocation).concat("/").concat(userMovie),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<TheaterDTO>>() {
				});
	}

	public ResponseEntity<List<ShowDetailsDTO>> getShowDetails(Integer userTheaterId, String movieName) {
		return restTemplate.exchange(
				apiUrl.concat("/showdetails/").concat(movieName).concat("?theaterId=")
						.concat(Integer.toString(userTheaterId)),
				HttpMethod.GET, null, new ParameterizedTypeReference<List<ShowDetailsDTO>>() {
				});
	}

	public ResponseEntity<SeatLayoutDTO> getSeatLayout(Integer userShowDetailsId) {
		return restTemplate.exchange(apiUrl.concat("/booking/").concat(Integer.toString(userShowDetailsId)),
				HttpMethod.GET, null, new ParameterizedTypeReference<SeatLayoutDTO>() {
				});
	}

	public ResponseEntity<TicketDTO> bookTicket(Integer userShowDetailsId, String username, String[] seats) {
		String bookTicketEndpoint = apiUrl.concat("/booking/").concat(Integer.toString(userShowDetailsId)).concat("/")
				.concat(username);

		return restTemplate.postForEntity(bookTicketEndpoint, seats, TicketDTO.class);
	}

	public ResponseEntity<UserDTO> getUser(String username) {
		return restTemplate.exchange(apiUrl.concat("/user/").concat(username), HttpMethod.GET, null,
				new ParameterizedTypeReference<UserDTO>() {
				});
	}

	public ResponseEntity<String> registerUser(UserDTO user) {
		return restTemplate.postForEntity(apiUrl.concat("/user"), user, String.class);
	}

	public ResponseEntity<String> checkUserExists(String username, String email) {
		return restTemplate.getForEntity(apiUrl.concat("/user/").concat(username).concat("/").concat(email),
				String.class);
	}

	public ResponseEntity<String> addLocation(String cityName) {
		return restTemplate.exchange(apiUrl.concat("/location/").concat(cityName), HttpMethod.POST, null,
				new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<String> deleteLocation(Integer locationId) {
		return restTemplate.exchange(apiUrl.concat("/location/").concat(Integer.toString(locationId)),
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<List<MovieDTO>> getAllMovies() {
		return restTemplate.exchange(apiUrl.concat("/movie/"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MovieDTO>>() {
				});
	}

	public ResponseEntity<String> addMovie(String name, Integer[] locationId) {
		return restTemplate.postForEntity(apiUrl.concat("/movie/").concat(name), locationId, String.class);
	}

	public ResponseEntity<String> deleteMovie(Integer movieId) {
		return restTemplate.exchange(apiUrl.concat("/movie/").concat(Integer.toString(movieId)), HttpMethod.DELETE,
				null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<List<TheaterDTO>> getAllTheaters() {
		return restTemplate.exchange(apiUrl.concat("/theater"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TheaterDTO>>() {
				});
	}

	public ResponseEntity<String> addTheater(String name, Integer locationId) {
		return restTemplate.exchange(
				apiUrl.concat("/theater/").concat(name).concat("/").concat(Integer.toString(locationId)),
				HttpMethod.POST, null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<String> deleteTheater(Integer theaterId) {
		return restTemplate.exchange(apiUrl.concat("/theater/").concat(Integer.toString(theaterId)), HttpMethod.DELETE,
				null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<List<SeatTypeDTO>> getAllSeatTypes() {
		return restTemplate.exchange(apiUrl.concat("/seatType"), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<SeatTypeDTO>>() {
				});
	}

	public ResponseEntity<String> addSeatType(SeatTypeDTO seatType) {
		return restTemplate.postForEntity(apiUrl.concat("/seatType"), seatType, String.class);
	}

	public ResponseEntity<String> deleteSeatType(Integer seatTypeId) {
		return restTemplate.exchange(apiUrl.concat("/seatType/").concat(Integer.toString(seatTypeId)),
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<String> editLocation(Integer locationId, String cityName) {
		return restTemplate.exchange(apiUrl.concat("/location/").concat("/").concat(Integer.toString(locationId))
				.concat("/").concat(cityName), HttpMethod.PUT, null, new ParameterizedTypeReference<String>() {
				});
	}

	public ResponseEntity<String> editMovie(Integer movieId, String name, Integer[] locationId) {
		return restTemplate.postForEntity(apiUrl.concat("/movie/").concat(Integer.toString(movieId)).concat("/").concat(name), locationId, String.class);

	}
}