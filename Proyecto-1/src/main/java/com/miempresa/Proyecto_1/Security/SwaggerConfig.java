package com.miempresa.Proyecto_1.Security;
	import io.swagger.v3.oas.models.OpenAPI;
	import io.swagger.v3.oas.models.info.Contact;
	import io.swagger.v3.oas.models.info.Info;
	import io.swagger.v3.oas.models.info.License;
	import io.swagger.v3.oas.models.servers.Server;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	
	import java.util.List;

@Configuration
public class SwaggerConfig {
		
	    @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Quiz App - API REST")
	                        .version("1.0.0")
	                        .description("API REST para la gestión de preguntas y cuestionarios. " +
	                                    "Permite realizar operaciones CRUD sobre preguntas de diferentes tipos: " +
	                                    "Verdadero/Falso, Selección Única y Selección Múltiple.")
	                        .contact(new Contact()
	                                .name("Equipo Quiz App")
	                                .email("soporte@quizapp.com")
	                                .url("https://github.com/tu-usuario/quiz-app"))
	                        .license(new License()
	                                .name("Apache 2.0")
	                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
	                .servers(List.of(
	                        new Server()
	                                .url("http://localhost:8080")
	                                .description("Servidor de Desarrollo"),
	                        new Server()
	                                .url("https://quizapp.com")
	                                .description("Servidor de Producción (ejemplo)")
	                ));
	    }
}

