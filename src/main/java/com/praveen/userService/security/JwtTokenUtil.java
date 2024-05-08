package com.praveen.userService.security;

import com.praveen.userService.configs.UserServiceConfiguration;
import com.praveen.userService.constants.UserServiceClaims;
import com.praveen.userService.models.Role;
import com.praveen.userService.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtTokenUtil {
    private final UserServiceConfiguration userServiceConfiguration;

    public JwtTokenUtil(UserServiceConfiguration userServiceConfiguration){
        this.userServiceConfiguration = userServiceConfiguration;
    }

    public String generateAuthToken(User user) {
        SecretKey secretKey = getSecretKey();

        Date expiryAt = new Date(new Date().getTime()
                + (long) userServiceConfiguration.getTokenExpirationInMinutes() * 60 * 1000);

        HashMap<String, String> claims = new HashMap<>();
        claims.put(UserServiceClaims.email, user.getEmail());
        claims.put(UserServiceClaims.userId, user.getId().toString());
        claims.put(UserServiceClaims.expiryAt, String.valueOf(expiryAt.getTime()/1000));

        List<String> roles = new ArrayList<>();

        for (Role role : user.getRoles()){
            roles.add(role.getRole());
        }

        claims.put(UserServiceClaims.roles, roles.toString());

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenExpired(String token){
        SecretKey secretKey = getSecretKey();

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiryAt = claims.getExpiration();

            return expiryAt == null || expiryAt.before(new Date());
        }
        catch (JwtException | IllegalArgumentException jwtException){
            return true;
        }
    }

    public ResponseCookie getResponseCookie(String token){
        return ResponseCookie
                .from(userServiceConfiguration.getTokenCookieName(), token)
                .path("/")
                .build();
    }

    private SecretKey getSecretKey() {
        return Keys
                .hmacShaKeyFor(userServiceConfiguration.getSecretKey().getBytes());
    }
}
