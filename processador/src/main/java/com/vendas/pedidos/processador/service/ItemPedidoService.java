package com.vendas.pedidos.processador.service;

import com.vendas.pedidos.processador.entity.ItemPedido;
import com.vendas.pedidos.processador.entity.Pedido;
import com.vendas.pedidos.processador.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository ;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }


    public List<ItemPedido> save(List<ItemPedido> itens , Pedido pedido) {

        itens.forEach( item -> {
            item.setPedido(pedido);
        });

        return itemPedidoRepository.saveAll(itens);

    }

    public void save(ItemPedido item){
        itemPedidoRepository.save(item);
    }

    public void updatedItemPedido(List<ItemPedido> itemPedidos, Pedido pedido) {
        itemPedidos.forEach( item -> {
            item.setPedido(pedido);  //definindo ao item o seu pedido
            this.save(item);
        });
    }
}
