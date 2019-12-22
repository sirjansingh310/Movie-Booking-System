package com.epam.mtbsclient.dto;

import java.time.LocalDateTime;

public class ShowDetailsDTO {
	private Integer id;
	private LocalDateTime localDateTime;
	private TheaterDTO theater;
	private MovieDTO movie;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public MovieDTO getMovie() {
		return movie;
	}
	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}
	public TheaterDTO getTheater() {
		return theater;
	}
	public void setTheater(TheaterDTO theater) {
		this.theater = theater;
	}
	@Override
	public String toString() {
		return "ShowDetailsDTO [id=" + id + ", localDateTime=" + localDateTime + ", theaterDTO=" + theater
				+ ", movieDTO=" + movie + "]";
	}
}
