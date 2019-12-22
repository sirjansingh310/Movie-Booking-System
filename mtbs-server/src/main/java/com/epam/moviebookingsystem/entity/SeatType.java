package com.epam.moviebookingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seat_type")
public class SeatType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String category;
	@Column(nullable = false, name = "number_of_rows")
	private Integer numberOfRows;
	@Column(nullable = false, name = "number_of_columns")
	private Integer numberOfColumns;
	@Column(nullable = false, name = "price")
	private Integer price;
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@ManyToOne
	@JoinColumn(nullable = false, name = "theater_id")
	private Theater theater;
	public SeatType() {
		super();
	}
	public SeatType(Integer id, String category, Integer numberOfRows, Integer numberOfColumns, Theater theater) {
		this.id = id;
		this.category = category;
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.theater = theater;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(Integer numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public Integer getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(Integer numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public Theater getTheater() {
		return theater;
	}
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	@Override
	public String toString() {
		return "SeatType [id=" + id + ", category=" + category + ", numberOfRows=" + numberOfRows + ", numberOfColumns="
				+ numberOfColumns + ", price=" + price + ", theater=" + theater + "]";
	}
}
