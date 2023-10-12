package com.projet.buyback.schema.response.spectacle;

import java.time.LocalDate;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.model.spectacle.SpectacleCategory;
import com.projet.buyback.schema.response.user.UserPublicResponse;

public class SpectacleResponse {

	private Long id;
	private String name;
	private Double price;
	private LocalDate startDate;
	private LocalDate endDate;
	private Address address;
	private String description;
	private SpectacleCategory category;
	private UserPublicResponse seller;
	private UserPublicResponse purchaser;

	public SpectacleResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public SpectacleResponse(
		Long id,
		String name,
		Double price,
		LocalDate startDate,
		LocalDate endDate,
		Address address,
		String description,
		SpectacleCategory category,
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

	public static SpectacleResponse createSpectacleResponse(Spectacle spectacle) {
		return new SpectacleResponse(
			spectacle.getId(),
			spectacle.getName(),
			spectacle.getPrice(),
			spectacle.getStartDate(),
			spectacle.getEndDate(),
			spectacle.getAddress(),
			spectacle.getDescription(),
			spectacle.getSpectacleCategory(),
			(spectacle.getSeller() != null)?
				new UserPublicResponse(
					spectacle.getSeller().getId(),
					spectacle.getSeller().getFirstname(),
					spectacle.getSeller().getLastname(),
					spectacle.getSeller().getEmail()
				):null,
			(spectacle.getPurchaser() != null)?
				new UserPublicResponse(
					spectacle.getPurchaser().getId(),
					spectacle.getPurchaser().getFirstname(),
					spectacle.getPurchaser().getLastname(),
					spectacle.getPurchaser().getEmail()
				):null
		);
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

	public SpectacleCategory getCategory() {
		return category;
	}

	public void setCategory(SpectacleCategory category) {
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
		return "SpectacleResponse{" +
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
