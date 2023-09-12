package com.example.medikitmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ComponentScan(basePackages = {"com.example.medikitmvc", "com.example.medikitcommon"})
@EntityScan("com.example.medikitcommon")
@EnableJpaRepositories(basePackages = "com.example.medikitcommon.repository")
@EnableAsync
@SpringBootApplication
public class MedIkItMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedIkItMvcApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
