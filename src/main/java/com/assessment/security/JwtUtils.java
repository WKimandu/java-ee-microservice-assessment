package com.assessment.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// TODO: Implement JWT generation and validation utilities
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // TODO: Externalize secret and expiration time into application properties
    @Value("${app.jwt.secret:DefaultSecretKeyNeedsToBeLongerThan256BitsForHS256}") // Use a strong, configured secret
    private String jwtSecret;

    @Value("${app.jwt.expirationMs:86400000}") // Default to 24 hours
    private int jwtExpirationMs;

    private SecretKey key() {
        // TODO: Ensure jwtSecret is properly configured and secure
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(Authentication authentication) {
        // TODO: Generate JWT token from Authentication object
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Use setSubject
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        // TODO: Extract username from JWT token
        // Removed incorrect .build() call
        return Jwts.parser()
                   .setSigningKey(key()) 
                   .parseClaimsJws(token) // Use parseClaimsJws
                   .getBody()
                   .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        // TODO: Validate the JWT token (signature, expiration, format)
        try {
            // Removed incorrect .build() call
            Jwts.parser().setSigningKey(key()).parseClaimsJws(authToken); // Use setSigningKey and parseClaimsJws
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (JwtException e) { // Catch broader JWT exceptions
             logger.error("JWT validation error: {}", e.getMessage());
        }

        return false;
    }
}

