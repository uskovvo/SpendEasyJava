package com.app.spendeasyjava.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Valerii",
                        email = "uskovvobdt@gmail.com"
                ),
                description = "OpenAPI documentation for SpendEasy App",
                title = "OpenApi specification",
                version = "0.0.1"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:" + "${server.port}"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://appforlife.duckdns.com/docs"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
