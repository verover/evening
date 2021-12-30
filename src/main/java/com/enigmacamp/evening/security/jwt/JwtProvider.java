package com.enigmacamp.evening.security.jwt;

import com.enigmacamp.evening.entity.User;
import com.enigmacamp.evening.service.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${evening.app.jwtSecret}")
    private String secret;
    private static String SECRET_KEY;

    @Value("${evening.app.jwtSecret}")
    public void setSecret(String secret) {
        this.SECRET_KEY = secret;
    }
    @Value("${evening.app.jwtExpirationMs}")
    private Integer expire;
    private static Integer EXPIRE_KEY;

    @Value("${evening.app.jwtExpirationMs}")
    public void setExpireKey(Integer expire) {
        this.EXPIRE_KEY = expire;
    }

    public String generateJwtToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRE_KEY);
        return Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .setId(userPrincipal.getId())
                    .setIssuedAt(new Date())
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
    }
    
    public String generateTokenFromUser(User user) {
        Instant expiryDate = Instant.now().plusMillis(EXPIRE_KEY);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(user.getId())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
 
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                      .setSigningKey(SECRET_KEY)
                      .parseClaimsJws(token)
                      .getBody().getSubject();
    }
    
    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
 
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public long getExpiryDuration() {
        return EXPIRE_KEY;
    }
}
