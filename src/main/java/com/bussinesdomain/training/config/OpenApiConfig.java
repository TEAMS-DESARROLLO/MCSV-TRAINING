package com.bussinesdomain.training.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Geraldo Achuy",
						email = "gfachuy@indracompany.com",
						url = "...."
				),
				description = "OpenApi documentaion for Training service",
				title = "API Training",
				version = "1.0.0"
		),
		servers = {
				@Server(
					description = "Local ENV",
					url = "http://localhost:9004"
				)
		}
)
public class OpenApiConfig {

}
