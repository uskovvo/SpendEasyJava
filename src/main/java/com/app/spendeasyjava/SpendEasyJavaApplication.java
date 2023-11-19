package com.app.spendeasyjava;

import com.app.spendeasyjava.domain.requests.AuthenticationRequest;
import com.app.spendeasyjava.domain.requests.RegisterRequest;
import com.app.spendeasyjava.domain.responses.Response;
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
            Response responseAdmin = service.register(admin);
            if(responseAdmin.getResult().equals("Success")) {
                AuthenticationRequest request = AuthenticationRequest
                        .builder()
                        .email("admin@admin.com")
                        .password("admin")
                        .build();
                System.out.println("Admin authorized: " + service.authenticate(request).getAccessToken());
            }

            var manager = RegisterRequest
                    .builder()
                    .name("Manager")
                    .email("manager@manager.com")
                    .password("manager")
                    .role(MANAGER)
                    .build();
            Response responseManager = service.register(manager);
            if(responseManager.getResult().equals("Success")) {
                AuthenticationRequest request = AuthenticationRequest
                        .builder()
                        .email("manager@manager.com")
                        .password("manager")
                        .build();
                System.out.println("Manager authorized: " + service.authenticate(request).getAccessToken());
            }
        };
    }

}
