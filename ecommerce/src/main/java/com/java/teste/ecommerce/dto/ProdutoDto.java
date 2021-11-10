package com.java.teste.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProdutoDto {
    private Long id;
    private String nome;
    private double preco;
}
