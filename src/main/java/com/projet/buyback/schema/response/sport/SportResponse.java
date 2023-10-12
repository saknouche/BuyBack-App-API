package com.projet.buyback.schema.response.sport;

import java.time.LocalDate;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.model.sport.SportCategory;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;
import com.projet.buyback.schema.response.user.UserPublicResponse;

public class SportResponse {

	private Long id;
	private String name;
	private Double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private Address address;
	private String description;
	private SportCategory category;
	private UserPublicResponse seller;
	private UserPublicResponse purchaser;

	public SportResponse() {
		super();
	}

	public SportResponse(
		Long id,
		String name,
		Double price,
		LocalDate startDate,
		LocalDate endDate,
		Address address,
		String description,
		SportCategory category,
		UserPublicResponse seller,
		UserPublicResponse purchaser
	) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.address = address;
		this.description = description;
		this.category = category;
		this.seller = seller;
		this.purchaser = purchaser;
	}

	public static SportResponse createSportResponse(Sport sport) {
		return new SportResponse(
			sport.getId(),
			sport.getName(),
			sport.getPrice(),
			sport.getStartDate(),
			sport.getEndDate(),
			sport.getAddress(),
			sport.getDescription(),
			sport.getSportCategory(),
			(sport.getSeller() != null)?
				new UserPublicResponse(
					sport.getSeller().getId(),
					sport.getSeller().getFirstname(),
					sport.getSeller().getLastname(),
					sport.getSeller().getEmail()
				):null,
			(sport.getPurchaser() != null)?
				new UserPublicResponse(
					sport.getPurchaser().getId(),
					sport.getPurchaser().getFirstname(),
					sport.getPurchaser().getLastname(),
					sport.getPurchaser().getEmail()
				):null
		);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public SportCategory getCategory() {
		return category;
	}

	public void setCategory(SportCategory category) {
		this.category = category;
	}

	public UserPublicResponse getSeller() {
		return seller;
	}

	public void setSeller(UserPublicResponse seller) {
		this.seller = seller;
	}

	public UserPublicResponse getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(UserPublicResponse purchaser) {
		this.purchaser = purchaser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SportResponse{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", startDate=" + startDate +
			", endDate=" + endDate +
			", address=" + address +
			", category=" + category +
			", seller=" + seller +
			", purchaser=" + purchaser +
			'}';
	}
}
