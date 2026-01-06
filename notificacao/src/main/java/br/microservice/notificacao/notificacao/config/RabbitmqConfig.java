package br.microservice.notificacao.notificacao.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exanche.name}")
    private String exchangeName ;

    @Value("${rabbitmq.queue.name}")
    private String queueName ;

    //CRIA A EXCHANGE PARA TESTES
    @Bean
    public FanoutExchange pedidoExchange(){
        return new FanoutExchange(exchangeName);
    }

    //para retornar dados da fila
    @Bean
    public Queue NotificationQueue (){
        return new Queue(queueName);
    }

    //faz o bind da Queue com a exchange
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(NotificationQueue()).to(pedidoExchange()) ;
    }


    //CRIA A CONEXAO COM RABBITM
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return  new RabbitAdmin(connectionFactory);
    }

    //CONVERTE OBJETOS EM JSON PARA EMVIAR AO RABBITMQ
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }

    // JSON CONVERTER (ENVIO + RECEBIMENTO)
    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }


    //RESPONSÁVEL POR ENVIAR A MENSAGEM
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory , MessageConverter messageConverter ){

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate ;
    }



    //INICIANDO TODA CONFIGURAÇÃO DO RABBIMQ E FICA OUVINDO A CONEXÃO
    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitAdmin rabbitAdmin){

//        return new ApplicationListener<ApplicationReadyEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationReadyEvent event) {
//                rabbitAdmin.initialize();
//            }
//        }

        return  event -> rabbitAdmin.initialize();


    }

}
