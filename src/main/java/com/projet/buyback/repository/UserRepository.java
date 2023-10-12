package com.projet.buyback.repository;

import java.util.Optional;

import com.projet.buyback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.projet.buyback.model.sport.Sport;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    boolean existsById(@NonNull Long id);
    
}
