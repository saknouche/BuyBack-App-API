package com.projet.buyback.repository.security;

import java.util.Optional;

import com.projet.buyback.model.security.ERole;
import com.projet.buyback.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
