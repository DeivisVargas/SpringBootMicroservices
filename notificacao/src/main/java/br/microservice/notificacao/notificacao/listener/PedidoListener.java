package br.microservice.notificacao.notificacao.listener;

import br.microservice.notificacao.notificacao.entity.Pedido;
import br.microservice.notificacao.notificacao.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private Logger logger  = LoggerFactory.getLogger(PedidoListener.class);

    @Value("${rabbitmq.queue.name}")
    private String queueName ;

    private final EmailService emailService ;

    public PedidoListener(EmailService emailService) {
        this.emailService = emailService;
    }


    @RabbitListener (queues = "pedidos.v1.pedido-criado-gerar-notificacao" )
    public void enviaNotificacao(Pedido pedido){

        emailService.enviarEmail(pedido);
        logger.info("Notificação gerada: {} " ,  pedido.toString());

    }

}
