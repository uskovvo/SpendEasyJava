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
                    .password("Admin1=")
                    .confirmPassword("Admin1=")
                    .role(ADMIN)
                    .build();
            Response responseAdmin = service.register(admin);
            if(responseAdmin.getResult().equals("Success")) {
                AuthenticationRequest request = AuthenticationRequest
                        .builder()
                        .email("admin@admin.com")
                        .password("Admin1=")
                        .build();
                System.out.println("Admin authorized: " + service.authenticate(request).getAccessToken());
            }

            var manager = RegisterRequest
                    .builder()
                    .name("Manager")
                    .email("manager@manager.com")
                    .password("Manager1=")
                    .confirmPassword("Manager1=")
                    .role(MANAGER)
                    .build();
            Response responseManager = service.register(manager);
            if(responseManager.getResult().equals("Success")) {
                AuthenticationRequest request = AuthenticationRequest
                        .builder()
                        .email("manager@manager.com")
                        .password("Manager1=")
                        .build();
                System.out.println("Manager authorized: " + service.authenticate(request).getAccessToken());
            }
        };
    }

}
