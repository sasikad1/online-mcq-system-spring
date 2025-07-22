package com.sasika.mcq.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Optional: Skip null values during mapping
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        return modelMapper;
    }
}