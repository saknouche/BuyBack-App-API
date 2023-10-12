package com.projet.buyback.repository.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.sport.SportCategory;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Long> {
	boolean existsById(@NonNull Long id);
}
