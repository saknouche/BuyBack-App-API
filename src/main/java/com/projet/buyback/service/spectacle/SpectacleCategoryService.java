package com.projet.buyback.service.spectacle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.buyback.model.spectacle.SpectacleCategory;
import com.projet.buyback.repository.spectacle.SpectacleCategoryRepository;

@Service
public class SpectacleCategoryService {

	@Autowired
	private SpectacleCategoryRepository spectacleCategoryRepository;
	
	public List<SpectacleCategory> getAllSpectacleCategories() {
		return spectacleCategoryRepository.findAll();
	}

	public Optional<SpectacleCategory> getSpectacleCategoryById(Long id) {
		return spectacleCategoryRepository.findById(id); 
	}
	
	public void deleteSpectacleCategoryById(Long id) {		
		spectacleCategoryRepository.deleteById(id);
	}
}
