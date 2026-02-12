package com.hotel_booking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hotelBookingOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Booking System API")
                        .description("Production-ready Hotel Booking Backend with JWT, S3, Payment Simulation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")
                        )
                        .license(new License()
                                .name("Private License")
                        )
                );
    }
}
