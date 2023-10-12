package com.projet.buyback.repository.spectacle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.spectacle.SpectacleCategory;

@Repository
public interface SpectacleCategoryRepository extends JpaRepository<SpectacleCategory, Long> {

}  
