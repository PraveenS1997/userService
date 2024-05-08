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

    public boolean isTokenValid(String token){
        try {
            Claims claims = getClaims(token);

            Date expiryAt = claims.getExpiration();

            return expiryAt != null && !expiryAt.before(new Date());
        }
        catch (JwtException | IllegalArgumentException jwtException){
            return false;
        }
    }

    public ResponseCookie getResponseCookie(String token){
        return ResponseCookie
                .from(userServiceConfiguration.getTokenCookieName(), token)
                .path("/")
                .build();
    }

    // Todo: Yet to complete the is admin user logic
    public boolean isAdminUser(String token) {
        if(!isTokenValid(token)){
            return false;
        }

        Claims claims = getClaims(token);
        List<String> roles = (List<String>) claims.get(UserServiceClaims.roles);

        return roles
                .stream()
                .anyMatch(r -> r.equalsIgnoreCase("Admin"));
    }

    private SecretKey getSecretKey() {
        return Keys
                .hmacShaKeyFor(userServiceConfiguration.getSecretKey().getBytes());
    }

    private Claims getClaims(String token){
        SecretKey secretKey = getSecretKey();

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
