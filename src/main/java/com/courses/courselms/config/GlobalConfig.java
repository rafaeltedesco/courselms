package com.courses.courselms.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GlobalConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
