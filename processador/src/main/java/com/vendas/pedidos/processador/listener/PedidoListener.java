package com.vendas.pedidos.processador.listener;


import com.vendas.pedidos.processador.entity.Pedido;
import com.vendas.pedidos.processador.entity.enums.Status;
import com.vendas.pedidos.processador.repository.PedidoRepository;
import com.vendas.pedidos.processador.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private Logger logger  = LoggerFactory.getLogger(PedidoListener.class);

    @Value("${rabbitmq.queue.name}")
    private String queueName ;

    private final PedidoService pedidoService  ;

    public PedidoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;

    }


    @RabbitListener(queues = "pedidos.v1.pedido-criado-gerar-processamento" )
    public void salvarPedido(Pedido pedido){
        pedido.setStatus(Status.PROCESSADO);
        pedidoService.save(pedido);

    }

}
