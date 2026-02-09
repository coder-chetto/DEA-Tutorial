package com.tutorial.Students.Logs.config;

import com.tutorial.Students.Logs.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.createRole("ADMIN");
        userService.createRole("USER");
    }
}
