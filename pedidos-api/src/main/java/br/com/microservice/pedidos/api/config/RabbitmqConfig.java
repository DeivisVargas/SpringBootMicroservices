package br.com.microservice.pedidos.api.config;


import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
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
import org.springframework.messaging.converter.MessageConversionException;

@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.exanche.name}")
    private String exchangeName ;

    //CRIA A EXCHANGE PARA TESTES
    @Bean
    public Exchange pedidosExchange(){

        //TIPO DE EXCHANGE QUE ENVIA A TODAS AS FILAS VINCULADAS A ELA
        return new FanoutExchange(exchangeName);
    }

    //CRIA A CONEXAO COM RABBITMQ
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return  new RabbitAdmin(connectionFactory);
    }

    //CONVERTE OBJETOS EM JSON PARA EMVIAR AO RABBITMQ
    @Bean
    public MessageConverter messageConverter(){
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
