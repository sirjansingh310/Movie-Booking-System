package com.epam.moviebookingsystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = {
		"show_details_id", "seats"
}))
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String seats;
	@Column(nullable = false, name = "booking_timestamp")
	private LocalDateTime bookingTimestamp;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Column(name = "amount_paid", nullable = false)
	private Double amountPaid;
	@ManyToOne
	@JoinColumn(name = "show_details_id", nullable = false)
	private ShowDetails showDetails;
	public LocalDateTime getBookingTimestamp() {
		return bookingTimestamp;
	}
	public void setBookingTimestamp(LocalDateTime bookingTimestamp) {
		this.bookingTimestamp = bookingTimestamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Booking() {
		super();
	}
	public Booking(Integer id, String seats, ShowDetails showDetails) {
		super();
		this.id = id;
		this.seats = seats;
		this.showDetails = showDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public ShowDetails getShowDetails() {
		return showDetails;
	}

	public void setShowDetails(ShowDetails showDetails) {
		this.showDetails = showDetails;
	}
	@Override
	public String toString() {
		return "Booking [id=" + id + ", seats=" + seats + ", localDateTime=" + bookingTimestamp + ", user=" + user
				+ ", amountPaid=" + amountPaid + ", showDetails=" + showDetails + "]";
	}
}
