package br.com.microservice.pedidos.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest API- Microserviços")
                                .description("Api para realizar pedidos")
                                .contact( new Contact().name("Deivis").email("deivisvp@yahoo.com"))
                );
    }
}
