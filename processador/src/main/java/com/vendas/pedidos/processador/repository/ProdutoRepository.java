package com.vendas.pedidos.processador.repository;

import com.vendas.pedidos.processador.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto , UUID> {

}
