package com.projet.buyback.model.spectacle;

import java.time.LocalDate;

import com.projet.buyback.model.Address;
import com.projet.buyback.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "spectacle")
public class Spectacle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;
	@Column(name = "description")
	private String description;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "address_id" , referencedColumnName = "id")
	private Address address;
	@ManyToOne
	@JoinColumn(name = "spectacle_category_id", referencedColumnName = "id")
	private SpectacleCategory spectacleCategory;
	@ManyToOne
	@JoinColumn(name = "seller_id", referencedColumnName = "id")
	private User seller;
	@ManyToOne
	@JoinColumn(name = "purchaser_id", referencedColumnName = "id")
	private User purchaser;

	public Spectacle() {
		super();
	}

	public Spectacle(String name, Double price, LocalDate startDate, LocalDate endDate) {
		super();
		this.name = name;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public SpectacleCategory getSpectacleCategory() {
		return spectacleCategory;
	}

	public void setSpectacleCategory(SpectacleCategory spectacleCategory) {
		this.spectacleCategory = spectacleCategory;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public User getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(User purchaser) {
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
		return "Spectacle [id=" + id + ", name=" + name + ", price=" + price + ", startDate=" + startDate + ", endDate="
				+ endDate + ", address=" + address + ", spectacleCategory=" + spectacleCategory + ", seller="
				+ seller + ", purchaser=" + purchaser + "]";
	}

}