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

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exanche.name}")
    private String exchangeName ;

    @Value("${rabbitmq.exanche.dlxname}")
    private String exchangeDlxName ;


    @Value("${rabbitmq.queue.name}")
    private String queueName ;

    @Value("${rabbitmq.queue.dlqname}")
    private String queueDlqName ;

    //CRIA A EXCHANGE PARA TESTES
    @Bean
    public FanoutExchange pedidoExchange(){
        return new FanoutExchange(exchangeName);
    }

    //cria a exchange para DLX
    @Bean
    public FanoutExchange pedidoDlxExchange(){
        return new FanoutExchange(exchangeDlxName);
    }


    //CRIA A FILA DE NOTIFICAÇÃO
    @Bean
    public Queue NotificationQueue (){
        //configurado apos dar todas as tentativas e dar erro , para a mensagem ser encaminhada para a fola DLX
        Map<String , Object> argumentos = new HashMap<>();
        argumentos.put("x-dead-letter-exchange" , exchangeDlxName) ;

        return new Queue(queueName, true, false ,false, argumentos);
    }

    //faz o bind da Queue com a exchange
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(NotificationQueue()).to(pedidoExchange()) ;
    }

    //CRIA A FILA DE DLX
    @Bean
    public Queue NotificationdLXQueue (){
        return new Queue(queueDlqName);
    }

    //FAZ O BIND COM DA FILA COM A EXCHANGE DLX
    @Bean
    public Binding bindingDlq(){
        return BindingBuilder.bind(NotificationdLXQueue()).to(pedidoDlxExchange()) ;
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
