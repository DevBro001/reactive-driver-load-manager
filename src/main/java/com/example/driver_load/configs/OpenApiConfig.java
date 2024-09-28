package com.example.driver_load.configs;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
       info = @Info(
           title = "Reactive Driver and Load Controller ",
           description = "Crud and listing Driver Load and Users!",
           contact = @Contact(
                   name = "BigBro",
                   url = "https://www.linkedin.com/in/boburerkinov/",
                   email = "boburerkinov002@gmail.com"
           ),
           version = "v0.0.1"
       ),
       servers = {
               @Server(url = "http://localhost:8080",description = "For Local Mode")
//               @Server(url = "http://123.10.10.4.18:8080",description = "For Production Mode")
       }
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerAuth",
        description = "You have to add JWT Token",
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
