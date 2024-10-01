package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos") // Usar plural para a URL
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody @Valid Produto produto) {
        // Validações adicionais com base no tipo do produto
        if ("veiculo".equals(produto.getTipo()) && (produto.getAno() == null || produto.getPlaca() == null)) {
            logger.warn("Tentativa de criar veículo sem ano ou placa.");
            return ResponseEntity.badRequest().body(null); // Retorna erro se informações de veículo estiverem faltando
        }

        // Salva o produto
        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto criado: {}", savedProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produto.setId(id);
        Produto updatedProduto = produtoRepository.save(produto);
        logger.info("Produto atualizado: {}", updatedProduto);
        return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        logger.info("Produto deletado: ID {}", id);
        return ResponseEntity.noContent().build();
    }
}
