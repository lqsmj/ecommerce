package com.java.teste.ecommerce.service;

import com.java.teste.ecommerce.dto.ProdutoDto;
import com.java.teste.ecommerce.entity.Produto;
import com.java.teste.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Método para salvar o produto
     * @param produtoDto
     * @return
     */
    public Produto prepararProduto(ProdutoDto produtoDto){
        Produto produto = new Produto();
        produto.setNome(produtoDto.getNome());
        produto.setPreco(produtoDto.getPreco());
        return produtoRepository.save(produto);
    }


    /**
     * Método para buscar o produto a partir do id
     * @param id
     * @return
     */
    public Optional<Produto> buscarProdutoPorId(Long id){
        return produtoRepository.findById(id);
    }


    /**
     * Método para buscar todos os produtos
     * @return
     */
    public List<Produto> listaProduto(){
        return produtoRepository.findAll();
    }

    /**
     * Método para remover o produto a partir do id
     * @param id
     */
    public void removerProdutoPorId(Long id){
        produtoRepository.deleteById(id);
    }

}
