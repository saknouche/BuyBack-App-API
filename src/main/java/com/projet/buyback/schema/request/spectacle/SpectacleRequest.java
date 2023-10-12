package com.projet.buyback.schema.request.spectacle;

import java.time.LocalDate;

public class SpectacleRequest {

	private String name;
	private Double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private String addressName;
	private String addressZipcode;
	private Long spectacleCategoryId;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressZipcode() {
		return addressZipcode;
	}

	public void setAddressZipcode(String addressZipcode) {
		this.addressZipcode = addressZipcode;
	}

	public Long getSpectacleCategoryId() {
		return spectacleCategoryId;
	}

	public void setSpectacleCategoryId(Long spectacleCategoryId) {
		this.spectacleCategoryId = spectacleCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SpectacleRequest [name=" + name + ", price=" + price + ", startDate=" + startDate + ", endDate="
				+ endDate + ", addressName=" + addressName + ", addressZipcode=" + addressZipcode
				+ ", spectacleCategoryId=" + spectacleCategoryId + "]";
	}

}
