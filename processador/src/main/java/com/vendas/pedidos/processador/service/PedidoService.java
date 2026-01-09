package com.vendas.pedidos.processador.service;

import com.vendas.pedidos.processador.entity.ItemPedido;
import com.vendas.pedidos.processador.entity.Pedido;
import com.vendas.pedidos.processador.listener.PedidoListener;
import com.vendas.pedidos.processador.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PedidoService {

    private Logger logger  = LoggerFactory.getLogger(PedidoService.class);
    private final PedidoRepository pedidoRepository ;
    private final ProdutoService produtoService ;
    private final ItemPedidoService itemPedidoService ;


    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoService produtoService,
                         ItemPedidoService itemPedidoService) {

        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
        this.itemPedidoService = itemPedidoService;

    }

    public void save(Pedido pedido){
        //salvando os produtos
        produtoService.save(pedido.getItens()) ;

        //salvamos itens do pedido
        //List<ItemPedido> itemPedidos = itemPedidoService.save(pedido.getItens() , pedido);

        //salvando o pedido
        //pedidoRepository.save(pedido);


        //atualiza o itemPedido definindo o pedido ao qual ele faz parte
        //itemPedidoService.updatedItemPedido(itemPedidos , pedido) ;

        logger.info("Pedido salvo ID : {} " ,  pedido.getId());

    }
}
