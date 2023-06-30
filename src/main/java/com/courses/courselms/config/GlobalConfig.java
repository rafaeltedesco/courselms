package com.courses.courselms.config;

import com.courses.courselms.services.adapters.BcryptAdapter;
import com.courses.courselms.services.ports.IHashEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class GlobalConfig {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    IHashEncoder getHashEncoder() { return new BcryptAdapter(); }

}
