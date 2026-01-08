package com.vendas.pedidos.processador.repository;

import com.vendas.pedidos.processador.entity.ItemPedido;
import com.vendas.pedidos.processador.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {


}
