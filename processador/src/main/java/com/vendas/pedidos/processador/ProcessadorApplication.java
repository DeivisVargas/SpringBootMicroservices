package com.vendas.pedidos.processador;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ProcessadorApplication {

	public static void main(String[] args) {

        SpringApplication.run(ProcessadorApplication.class, args);
        log.info("Aplicação iniciada com sucesso! porta 8082");
	}

}
