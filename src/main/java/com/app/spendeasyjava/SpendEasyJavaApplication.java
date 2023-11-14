package com.app.spendeasyjava;

import com.app.spendeasyjava.domain.DTO.RegisterRequest;
import com.app.spendeasyjava.domain.enums.Role;
import com.app.spendeasyjava.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.app.spendeasyjava.domain.enums.Role.ADMIN;
import static com.app.spendeasyjava.domain.enums.Role.MANAGER;

@SpringBootApplication
public class SpendEasyJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpendEasyJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var admin = RegisterRequest
                    .builder()
                    .name("Admin")
                    .email("admin@admin.com")
                    .password("admin")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var manager = RegisterRequest
                    .builder()
                    .name("Manager")
                    .email("manager@manager.com")
                    .password("manager")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());
        };
    }

}
