package kth.numi.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // all endpoints
                        .allowedOrigins("http://localhost:4000", "https://patient-journal.app.cloud.cbh.kth.se")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);
            }
        };
    }
}