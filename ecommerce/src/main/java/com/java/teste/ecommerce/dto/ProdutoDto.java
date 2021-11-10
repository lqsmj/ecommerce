package com.java.teste.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProdutoDto {
    private Long id;
    private String nome;
    private double preco;
}
