package com.praveen.userService.configs;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configuration annotation is used to define a class as a configuration class.
@Getter
@Configuration
public class UserServiceConfiguration {
    // The return value of this method will be registered as a bean in the Spring application context.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
