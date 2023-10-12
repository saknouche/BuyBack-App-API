package com.projet.buyback.service.sport;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.buyback.model.sport.SportCategory;
import com.projet.buyback.repository.sport.SportCategoryRepository;

@Service
public class SportCategoryService {
	@Autowired
	private SportCategoryRepository sportCategoryRepository;
	
	public List<SportCategory> getAllSportCategories() {
		return sportCategoryRepository.findAll();
	}

	public Optional<SportCategory> getSportCategoryById(Long id) {
		return sportCategoryRepository.findById(id); 
	}
	
	public void deleteSportCategoryById(Long id) {		
		sportCategoryRepository.deleteById(id);

	}
	
	public boolean isSportCategoryExists(Long id) { 
		return sportCategoryRepository.existsById(id);
	}
}
