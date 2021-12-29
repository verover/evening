package com.enigmacamp.evening.security.jwt;

import com.enigmacamp.evening.entity.UserDetailImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${evening.app.jwtSecret}")
    private String jwtSecret;

    @Value("${evening.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${evening.app.expiration.time}")
    private Long jwtExpirationInMillis;

    public String generateJwtToken(Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is epired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenWithUserName(String username) throws UnrecoverableKeyException, NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from((Instant.now())))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
