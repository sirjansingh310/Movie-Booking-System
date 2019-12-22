package com.epam.moviebookingsystem.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "show_details")
public class ShowDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "date_and_time", nullable = false)
	private LocalDateTime localDateTime;

	@ManyToOne
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "theater_id", nullable = false)
	private Theater theater;

	public ShowDetails() {
		super();
	}

	public ShowDetails(LocalDateTime localDateTime, Movie movie, Theater theater) {
		this.localDateTime = localDateTime;
		this.movie = movie;
		this.theater = theater;
	}

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

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Theater getTheater() {
		return theater;
	}

	public void setTheater(Theater theater) {
		this.theater = theater;
	}

	@Override
	public String toString() {
		return "ShowDetails{" + "id=" + id + ", localDateTime=" + localDateTime + ", movie=" + movie + ", theater="
				+ theater + '}';
	}
}
