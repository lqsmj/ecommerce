package com.java.teste.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarrinhoResponseDto {

    private Integer numeroCarrinho;
    private String email;
    private List<ItemCarrinhoResponseDto> listaItemCarrinhoResponse;
    private double totalCompra;
}
