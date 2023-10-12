package com.projet.buyback.repository.security;

import java.util.Optional;

import com.projet.buyback.model.security.RefreshToken;
import com.projet.buyback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Long userId);

    @Modifying
    int deleteByUser(User user);
}
