package com.java.teste.ecommerce.repository;

import com.java.teste.ecommerce.entity.ItemCarrinho;
import com.java.teste.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

    Optional<ItemCarrinho> findFirstByEmail(String email);


    ItemCarrinho findTopByOrderByNumeroCarrinhoDesc();

    ItemCarrinho findTopByEmailAndProduto(String email, Produto produto);

    List<ItemCarrinho> findByEmail(String email);

}
