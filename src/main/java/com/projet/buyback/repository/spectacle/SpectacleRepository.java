package com.projet.buyback.repository.spectacle;

import com.projet.buyback.model.sport.Sport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.spectacle.Spectacle;

import java.awt.print.Pageable;
import java.util.List;
import com.projet.buyback.model.User;


@Repository
public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {

	List<Spectacle> findBySellerAndPurchaserIsNotNull(User user);
	List<Spectacle> findBySellerAndPurchaserIsNull(User user);
	List<Spectacle> findByPurchaser(User user);

	@Query("SELECT spectacle FROM Spectacle spectacle " +
		"WHERE (:seller IS NULL OR spectacle.seller != :seller) " +
		"AND spectacle.purchaser is null AND (spectacle.name LIKE :like OR spectacle.spectacleCategory.name LIKE :like) "
		+ "ORDER BY spectacle.startDate ASC")
	List<Spectacle> findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(@Param("seller") User seller, @Param("like") String like);

	@Query("SELECT spectacle FROM Spectacle spectacle " +
		"WHERE (:seller IS NULL OR spectacle.seller != :seller) " +
		"AND spectacle.purchaser is null "
		+ "ORDER BY spectacle.startDate ASC")
	List<Spectacle> findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(@Param("seller") User seller);

	@Query("SELECT spectacle FROM Spectacle spectacle " +
		"WHERE (:seller IS NULL OR spectacle.seller != :seller) " +
		"AND spectacle.purchaser is null "
		+ "ORDER BY spectacle.startDate ASC")
	List<Spectacle> findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(@Param("seller") User seller, PageRequest limit);

}
