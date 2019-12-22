package com.epam.mtbsclient.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.mtbsclient.dto.LocationDTO;
import com.epam.mtbsclient.dto.MovieDTO;
import com.epam.mtbsclient.dto.SeatLayoutDTO;
import com.epam.mtbsclient.dto.SeatTypeDTO;
import com.epam.mtbsclient.dto.ShowDetailsDTO;
import com.epam.mtbsclient.dto.TheaterDTO;
import com.epam.mtbsclient.dto.TicketDTO;
import com.epam.mtbsclient.dto.UserDTO;
import com.epam.mtbsclient.service.RestClientService;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mockpassword", roles = "USER")
class BookingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RestClientService restClientServiceMock;
	private Principal mockPrincipal;
	private UserDTO userMock;
	private TicketDTO ticketDTO;
	private ShowDetailsDTO showDetailsDTO;
	private TheaterDTO theaterDTO;

	@BeforeEach
	void init() {
		mockPrincipal = mock(Principal.class);
		userMock = new UserDTO();
		userMock.setUsername("mock");
		userMock.setEmail("mock@mock.com");
		ticketDTO = new TicketDTO();
		ticketDTO.setId(1);
		ticketDTO.setSeats("A1-1 A2-1 ");
		showDetailsDTO = new ShowDetailsDTO();
		showDetailsDTO.setMovie(new MovieDTO());
		showDetailsDTO.setLocalDateTime(LocalDateTime.now());
		theaterDTO = new TheaterDTO();
		theaterDTO.setLocation(new LocationDTO());
		showDetailsDTO.setTheater(theaterDTO);
		ticketDTO.setShowDetails(showDetailsDTO);
		ticketDTO.setAmountPaid(354.0);
		ticketDTO.setUser(userMock);
	}

	@Test
	void getSeatLayoutTest() throws Exception {
		SeatLayoutDTO seatLayoutDTO = new SeatLayoutDTO();
		ResponseEntity<SeatLayoutDTO> responseEntity = new ResponseEntity<>(seatLayoutDTO, HttpStatus.OK);
		when(restClientServiceMock.getSeatLayout(1)).thenReturn(responseEntity);
		mockMvc.perform(post("/seatLayout").param("userShowDetailsId", "1")).andExpect(status().isOk())
				.andExpect(view().name("bookseats"));
	}

	@Test
	void getEmptySeatLayoutTest() throws Exception {
		ResponseEntity<SeatLayoutDTO> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		when(restClientServiceMock.getSeatLayout(1)).thenReturn(responseEntity);
		mockMvc.perform(post("/seatLayout").param("userShowDetailsId", "1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:pageNotFound"));
	}

	@Test
	void internalServerErrorTestForGetSeatLayout() throws Exception {
		ResponseEntity<SeatLayoutDTO> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.getSeatLayout(1)).thenReturn(responseEntity);
		mockMvc.perform(post("/seatLayout").param("userShowDetailsId", "1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:error"));
	}

	@Test
	void bookTicketTest() throws Exception {
		String seats[] = { "A1-1", "A2-1" };
		ResponseEntity<TicketDTO> responseEntity = new ResponseEntity<>(ticketDTO, HttpStatus.OK);
		when(mockPrincipal.getName()).thenReturn("mock");
		when(restClientServiceMock.getUser("mock")).thenReturn(new ResponseEntity<UserDTO>(userMock, HttpStatus.OK));
		when(restClientServiceMock.bookTicket(1,  "mock", seats)).thenReturn(responseEntity);
		List<SeatTypeDTO> seatTypeList = new ArrayList<>();
		mockMvc.perform(
				post("/printTicket").param("seats", "A1-1").param("seats", "A2-1").sessionAttr("seatTypeList", seatTypeList).sessionAttr("userShowDetailsId", 1).sessionAttr("isTicketPrinted", false))		
		.andExpect(status().isOk()).andExpect(view().name("ticket")); 
	}

	@Test
	void bookTicketServiceUnavailableTest() throws Exception {
		String seats[] = { "A1-1", "A2-1" };
		when(mockPrincipal.getName()).thenReturn("mock");
		when(restClientServiceMock.getUser("mock")).thenReturn(new ResponseEntity<UserDTO>(userMock, HttpStatus.OK));
		ResponseEntity<TicketDTO> responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		when(restClientServiceMock.bookTicket(1, "mock", seats)).thenReturn(responseEntity);
		mockMvc.perform(
				post("/printTicket").param("seats", "A1-1").param("seats", "A2-1").sessionAttr("userShowDetailsId", 1).sessionAttr("isTicketPrinted", false).principal(mockPrincipal))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:error"));
	} 

	@Test 
	void internalServerErrorTestForBookTicket() throws Exception {
		when(mockPrincipal.getName()).thenReturn("mock");
		when(restClientServiceMock.getUser("mock")).thenReturn(new ResponseEntity<UserDTO>(userMock, HttpStatus.OK));
		String seats[] = { "A1-1", "A2-1" };
		ResponseEntity<TicketDTO> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		when(restClientServiceMock.bookTicket(1,"mock",seats)).thenReturn(responseEntity);
		Principal mockPrincipal = mock(Principal.class);
		when(mockPrincipal.getName()).thenReturn("mock");
		mockMvc.perform(post("/printTicket").param("seats", "A1").param("seats", "A2")
				.sessionAttr("userShowDetailsId", 1).sessionAttr("isTicketPrinted", false).principal(mockPrincipal)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:error"));
	}
}
