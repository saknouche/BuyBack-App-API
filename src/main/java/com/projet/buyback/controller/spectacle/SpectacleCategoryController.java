package com.projet.buyback.controller.spectacle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.buyback.model.spectacle.SpectacleCategory;
import com.projet.buyback.service.spectacle.SpectacleCategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}/spectacle")
public class SpectacleCategoryController {
	@Autowired
	private SpectacleCategoryService spectacleCategoryService;
	
	@GetMapping("/categories")
	public ResponseEntity<List<SpectacleCategory>> getAllSpectacleTickets() {
		List<SpectacleCategory> spectacleCategories = spectacleCategoryService.getAllSpectacleCategories();
		if (!spectacleCategories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleCategories); 
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

}
