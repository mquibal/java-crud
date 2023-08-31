package com.example.demo.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EmployeeConfig {

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository repository ) {
        return args -> {
            Employee mariel = new Employee(
                    "mariel",
                    "quibal",
                    "marielquibal@gmail.com",
                    "09561441650",
                    "webdev"
            );

            Employee mara = new Employee(
                    "mara",
                    "quibal",
                    "maraquibal@gmail.com",
                    "09561441650",
                    "insurance"
            );

            repository.saveAll(
                    List.of(mariel, mara)
            );
        };
    }
}
