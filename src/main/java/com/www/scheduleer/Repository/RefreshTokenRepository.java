package com.www.scheduleer.Repository;

import com.www.scheduleer.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUserId(String userId);

    RefreshToken findByToken(String token);
}