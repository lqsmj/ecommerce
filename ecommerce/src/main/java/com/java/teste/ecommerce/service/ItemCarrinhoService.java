package com.java.teste.ecommerce.service;

import com.java.teste.ecommerce.dto.ItemCarrinhoDto;
import com.java.teste.ecommerce.entity.ItemCarrinho;
import com.java.teste.ecommerce.entity.Produto;
import com.java.teste.ecommerce.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCarrinhoService {

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    /**
     * Método para salvar o itemCarrinho
     * @param itemCarrinhoDto
     * @return
     */
    public ItemCarrinho prepararItemCarrinho(ItemCarrinhoDto itemCarrinhoDto){
        ItemCarrinho itemCarrinho = new ItemCarrinho();

        Produto produto = new Produto();
        produto.setId(itemCarrinhoDto.getIdProduto());


        ItemCarrinho ic = itemCarrinhoRepository.findTopByEmailAndProduto(itemCarrinhoDto.getEmail(),produto);
        if(ic!=null){
            ic.setQuantidade(itemCarrinhoDto.getQuantidade());
            return itemCarrinhoRepository.save(ic);
        }else{
            itemCarrinho.setProduto(produto);
            itemCarrinho.setEmail(itemCarrinhoDto.getEmail());
            itemCarrinho.setQuantidade(itemCarrinhoDto.getQuantidade());
            itemCarrinho.setNumeroCarrinho(getNumeroCarrinho(itemCarrinhoDto.getEmail()));
            return itemCarrinhoRepository.save(itemCarrinho);
        }

    }

    /**
     * Método para buscar todos os itemCarrinho
     * @return
     */
    public List<ItemCarrinho> listarItensCarrinho(){
        return itemCarrinhoRepository.findAll();
    }

    /**
     * Método para buscar o itemCarrinho a partir do id
     * @param id
     * @return
     */
    public Optional<ItemCarrinho> buscarItemCarrinhoPorId(Long id){
        return itemCarrinhoRepository.findById(id);
    }

    /**
     * Método para remover o itemCarrinho a partir do id
     * @param id
     */
    public void removerItemCarrinhoPorId(Long id){
        itemCarrinhoRepository.deleteById(id);
    }

    /**
     * Método para verificar qual valor de numeroCarrinho deve ser salvo
     * @param email
     * @return
     */
    public Integer getNumeroCarrinho(String email){

        Integer numeroCarrinho = 1;
        try{

            Optional<ItemCarrinho> itemCarrinho = itemCarrinhoRepository.findFirstByEmail(email);

            if(!ObjectUtils.isEmpty(itemCarrinho)){
                numeroCarrinho = itemCarrinho.get().getNumeroCarrinho();
                return numeroCarrinho;
            }
            ItemCarrinho ic = itemCarrinhoRepository.findTopByOrderByNumeroCarrinhoDesc();
            if(ic!=null){
                numeroCarrinho = ic.getNumeroCarrinho()+1;
                return numeroCarrinho;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return numeroCarrinho;
    }
}
