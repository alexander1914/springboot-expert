package com.github.alexander1914.produtos_restfull.controller;

import com.github.alexander1914.produtos_restfull.entity.Produto;
import com.github.alexander1914.produtos_restfull.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/{id}")
    public Produto obterProdutoPorId(@PathVariable("id") String id){
        return  produtoRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Produto> buscarProdutoPorNome(@RequestParam("nome") String nome){
        return produtoRepository.findByNome(nome);
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto){
        System.out.println("Produto recebido: " + produto);

        var id = UUID.randomUUID().toString();
        produto.setId(id);

        produtoRepository.save(produto);
        return produto;
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable("id") String id,
                          @RequestBody Produto produto){

        produto.setId(id);
        produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable("id") String id){
        produtoRepository.deleteById(id);
    }
}
