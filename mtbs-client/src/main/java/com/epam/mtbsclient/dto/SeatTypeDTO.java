package com.epam.mtbsclient.dto;

public class SeatTypeDTO {
	private Integer id;
	private String category;
	private Integer numberOfRows;
	private Integer numberOfColumns;
	private TheaterDTO theater;
	private Integer price;
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
	public TheaterDTO getTheater() {
		return theater;
	}
	public void setTheater(TheaterDTO theater) {
		this.theater = theater;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "SeatTypeDTO [id=" + id + ", category=" + category + ", numberOfRows=" + numberOfRows
				+ ", numberOfColumns=" + numberOfColumns + ", theater=" + theater + ", price=" + price + "]";
	}
	
}
