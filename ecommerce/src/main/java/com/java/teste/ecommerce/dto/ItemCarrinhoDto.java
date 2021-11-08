package com.java.teste.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemCarrinhoDto {

    private Long id;
    private Long idProduto;
    private String  email;
    private Integer quantidade;

}
