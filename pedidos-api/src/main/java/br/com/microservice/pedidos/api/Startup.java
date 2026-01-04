package br.com.microservice.pedidos.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Startup {

	public static void main(String[] args) {

        SpringApplication.run(Startup.class, args);
        log.info("Aplicação iniciada com sucesso! porta 8080");


	}

}
