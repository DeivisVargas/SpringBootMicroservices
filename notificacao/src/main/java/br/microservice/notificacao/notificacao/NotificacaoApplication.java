package br.microservice.notificacao.notificacao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NotificacaoApplication {

	public static void main(String[] args) {

        SpringApplication.run(NotificacaoApplication.class, args);
        log.info("Aplicação iniciada com sucesso! porta 8081");
	}

}
