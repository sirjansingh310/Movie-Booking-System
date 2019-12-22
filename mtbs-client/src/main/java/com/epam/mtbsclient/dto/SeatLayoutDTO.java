package com.epam.mtbsclient.dto;

import java.util.List;

public class SeatLayoutDTO {
	private List<SeatTypeDTO> seatTypeList;
	private List<String> bookedSeats;
	public List<String> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public List<SeatTypeDTO> getSeatTypeList() {
		return seatTypeList;
	}
	public void setSeatTypeList(List<SeatTypeDTO> seatTypeList) {
		this.seatTypeList = seatTypeList;
	}
}
