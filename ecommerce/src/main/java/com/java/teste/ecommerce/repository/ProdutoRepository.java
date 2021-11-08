package com.java.teste.ecommerce.repository;

import com.java.teste.ecommerce.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
