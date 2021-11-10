package com.java.teste.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemCarrinhoResponseDto {

    private String nomeProduto;
    private Integer quantidade;
    private double preco;

}
