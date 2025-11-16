package com.example.questionapp.services;

import com.example.questionapp.dataAccess.RefreshTokenRepository;
import com.example.questionapp.entities.RefreshToken;
import com.example.questionapp.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Service for managing refresh tokens
 * Creates, validates and manages refresh tokens for JWT authentication
 */
@Service
public class RefreshTokenService {

    @Value("${refresh.token.expires.in}")  // Validity period in seconds from application.properties
    Long expireSeconds;

    private RefreshTokenRepository refreshTokenRepository;

    /**
     * Constructor for dependency injection
     * @param refreshTokenRepository Repository for refresh token database operations
     */
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Creates or updates a refresh token for a user
     * @param user The user the token is to be created for
     * @return The token string of the created refresh token
     */
    public String createRefreshToken(User user) {
        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        // If no token exists, create a new token
        if(token == null) {
            token = new RefreshToken();
            token.setUser(user);
        }
        // Generate a unique token string using UUID
        token.setToken(UUID.randomUUID().toString());
        // Set the expiry time to current time + configured seconds
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    /**
     * Finds a refresh token by user ID
     * @param userId The ID of the user
     * @return The refresh token found, or null
     */
    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    /**
     * Checks whether a refresh token has expired
     * @param token The refresh token to check
     * @return true if the token has expired, false otherwise
     */
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

}