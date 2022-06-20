package com.samtechblog.configurations;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AllBeansDeclaration {

    //model mapper instance here declaration
    @Bean
    public ModelMapper getModelMapper() {return new ModelMapper();}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
