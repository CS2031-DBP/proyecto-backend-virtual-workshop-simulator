package com.example.proyecto.UtilConfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UtilConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
