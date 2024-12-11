package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ProdutoNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    // Criação de produto
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produtoCriado = produtoService.criarProduto(produtoDTO);
            logger.info("Produto criado com sucesso: {}", produtoCriado);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
        } catch (Exception e) {
            logger.error("Erro ao criar produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar produtos
    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        if (produtos.isEmpty()) {
            logger.warn("Nenhum produto encontrado.");
            return ResponseEntity.noContent().build();
        }
        logger.info("Produtos listados: {}", produtos.size());
        return ResponseEntity.ok(produtos);
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        try {
            Produto produto = produtoService.getProdutoById(id);
            logger.info("Produto encontrado: {}", produto);
            return ResponseEntity.ok(produto);
        } catch (ProdutoNaoEncontradoException e) {
            logger.warn("Produto não encontrado com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            Produto produtoAtualizado = produtoService.updateProduto(id, produtoDTO);
            logger.info("Produto atualizado com sucesso: {}", produtoAtualizado);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (ProdutoNaoEncontradoException e) {
            logger.warn("Produto não encontrado para atualização com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Deletar produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        try {
            produtoService.deleteProduto(id);
            logger.info("Produto deletado com sucesso com ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (ProdutoNaoEncontradoException e) {
            logger.warn("Produto não encontrado para remoção com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
