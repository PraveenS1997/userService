package com.praveen.userService.services;

import com.praveen.userService.configs.UserServiceConfiguration;
import com.praveen.userService.constants.UserServiceClaims;
import com.praveen.userService.models.Role;
import com.praveen.userService.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Primary
public class JwtTokenServiceImpl implements JwtTokenService {
    private final UserServiceConfiguration userServiceConfiguration;

    public JwtTokenServiceImpl(UserServiceConfiguration userServiceConfiguration){
        this.userServiceConfiguration = userServiceConfiguration;
    }

    @Override
    public String generateAuthToken(User user) {
        SecretKey secretKey = Keys
                .hmacShaKeyFor(userServiceConfiguration.getSecretKey().getBytes());

        Date expiryAt = new Date(new Date().getTime() + (long) userServiceConfiguration.getTokenExpirationInMinutes() * 60 * 1000);

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

    @Override
    public boolean isTokenExpired(String token){
        SecretKey secretKey = Keys
                .hmacShaKeyFor(userServiceConfiguration.getSecretKey().getBytes());

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
}
