package com.praveen.userService.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configuration annotation is used to define a class as a configuration class.
@Getter
@Configuration
public class UserServiceConfiguration {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expiration.in.minutes}")
    private int tokenExpirationInMinutes;

    @Value("${jwt.cookie.name}")
    private String tokenCookieName;

    // The return value of this method will be registered as a bean in the Spring application context.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
