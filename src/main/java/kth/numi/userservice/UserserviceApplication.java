package kth.numi.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@OpenAPIDefinition(
		info = @Info(
				title = "User service",
				version = "1.0.0",
				description = "APIs for User service for Patient Journal website",
				termsOfService = "none for now"
		)
)
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	public GroupedOpenApi customOpenAPI() {
		return GroupedOpenApi.builder()
				.group("User_service_APIs")
				.pathsToMatch(
						"/user/**",
						"/staff/**",
						"/doctor/**",
						"/patient/**")
				.build();
	}
}
