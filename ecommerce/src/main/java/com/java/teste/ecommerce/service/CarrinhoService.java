package com.java.teste.ecommerce.service;


import com.java.teste.ecommerce.dto.CarrinhoRequestDto;
import com.java.teste.ecommerce.dto.CarrinhoResponseDto;
import com.java.teste.ecommerce.dto.ItemCarrinhoResponseDto;
import com.java.teste.ecommerce.entity.ItemCarrinho;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CarrinhoService {

    private static final Logger log = LoggerFactory.getLogger(CarrinhoService.class);
    private final KafkaTemplate<String, CarrinhoResponseDto> kafkaTemplate;
    private final String topic;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;



    public CarrinhoService(@Value("${topic.name.carrinho}") String topic, KafkaTemplate<String, CarrinhoResponseDto> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Método para preparar o Carrinho
     * @param carrinhoRequestDto
     * @return
     */
    public CarrinhoResponseDto prepararCarrinho(CarrinhoRequestDto carrinhoRequestDto) throws Exception {
        CarrinhoResponseDto carrinhoResponseDto = new CarrinhoResponseDto();
        carrinhoResponseDto.setEmail(carrinhoRequestDto.getEmail());
        List<ItemCarrinho> itemCarrinhoList = itemCarrinhoService.listarItensCarrinhoPorEmail(carrinhoRequestDto.getEmail());
        if (!itemCarrinhoList.isEmpty()){
            double valorTotal = 0;
            carrinhoResponseDto.setNumeroCarrinho(itemCarrinhoList.get(0).getNumeroCarrinho());
            List<ItemCarrinhoResponseDto> itemCarrinhoResponseDtos = new ArrayList<ItemCarrinhoResponseDto>();
            for(ItemCarrinho itemCarrinho : itemCarrinhoList){
                ItemCarrinhoResponseDto itemCarrinhoResponseDto = new ItemCarrinhoResponseDto();
                itemCarrinhoResponseDto.setQuantidade(itemCarrinho.getQuantidade());
                itemCarrinhoResponseDto.setNomeProduto(itemCarrinho.getProduto().getNome());
                double precoProduto = itemCarrinho.getQuantidade()*itemCarrinho.getProduto().getPreco();
                itemCarrinhoResponseDto.setPreco(precoProduto);
                itemCarrinhoResponseDtos.add(itemCarrinhoResponseDto);
                valorTotal = valorTotal+precoProduto;
            }
            carrinhoResponseDto.setListaItemCarrinhoResponse(itemCarrinhoResponseDtos);
            carrinhoResponseDto.setTotalCompra(valorTotal);
        }else{
            throw new Exception("Itens não encontrado.");
        }
        send(carrinhoResponseDto);
        return carrinhoResponseDto;
    }



    public void send(CarrinhoResponseDto carrinhoResponseDto){
        kafkaTemplate.send(topic, carrinhoResponseDto);
    }


}
