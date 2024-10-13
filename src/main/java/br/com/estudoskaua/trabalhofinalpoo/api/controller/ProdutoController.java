package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para gerenciar produtos em leilão.
 */
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    public ProdutoController(ProdutoRepository produtoRepository, LeilaoRepository leilaoRepository) {
        this.produtoRepository = produtoRepository;
        this.leilaoRepository = leilaoRepository;
    }

    /**
     * Listar todos os produtos cadastrados.
     *
     * @return Lista de produtos.
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAllProdutosComLances();
        logger.info("Listando todos os produtos. Total: {}", produtos.size());
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }


    /**
     * Buscar produto por ID.
     *
     * @param id ID do produto.
     * @return Produto correspondente ou 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findByIdComLances(id)
                .map(produto -> {
                    logger.info("Produto encontrado com ID: {}", id);
                    return ResponseEntity.ok(produto);
                })
                .orElseGet(() -> {
                    logger.warn("Produto com ID {} não encontrado.", id);
                    return ResponseEntity.notFound().build();
                });
    }


    /**
     * Criar um novo produto vinculado a um leilão.
     *
     * @param produtoDTO Dados do produto.
     * @return Produto criado e status HTTP.
     **/
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(produtoDTO.getLeilaoId());
        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão com ID {} não encontrado para vinculação.", produtoDTO.getLeilaoId());
            return ResponseEntity.badRequest().build();
        }
        Leilao leilao = leilaoOptional.get();
        Produto produto;
        if (produtoDTO.getTipoInformatica() != null) {
            DispositivoInformatica dispositivo = new DispositivoInformatica();
            dispositivo.setNome(produtoDTO.getNome());
            dispositivo.setDescricao(produtoDTO.getDescricao());
            dispositivo.setValor(produtoDTO.getValor());
            dispositivo.setImagemUrl(produtoDTO.getImagemUrl());
            dispositivo.setLeilao(leilao);
            dispositivo.setTipoInformatica(TipoInformatica.valueOf(produtoDTO.getTipoInformatica()));
            produto = dispositivo;
        } else if (produtoDTO.getTipoVeiculo() != null) {
            Veiculo veiculo = new Veiculo();
            veiculo.setNome(produtoDTO.getNome());
            veiculo.setDescricao(produtoDTO.getDescricao());
            veiculo.setValor(produtoDTO.getValor());
            veiculo.setImagemUrl(produtoDTO.getImagemUrl());
            veiculo.setLeilao(leilao);
            veiculo.setTipoVeiculo(TipoVeiculo.valueOf(produtoDTO.getTipoVeiculo()));
            veiculo.setMarca(produtoDTO.getMarca());
            veiculo.setModelo(produtoDTO.getModelo());
            veiculo.setAnoDeFabricacao(produtoDTO.getAnoDeFabricacao());
            produto = veiculo;
        } else {
            produto = new Produto();
            produto.setNome(produtoDTO.getNome());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setValor(produtoDTO.getValor());
            produto.setImagemUrl(produtoDTO.getImagemUrl());
            produto.setLeilao(leilao);
        }

        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Produto criado e vinculado ao leilão: {}", savedProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }


    /**
         * Desassociar um produto de um leilão.
         *
         * @param id ID do produto.
         * @return Produto desassociado ou 404 Not Found.
         */
    @PutMapping("/{id}/desassociar")
    public ResponseEntity<Produto> desassociarProduto(@PathVariable Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            logger.warn("Produto com ID {} não encontrado para desassociação.", id);
            return ResponseEntity.notFound().build();
        }
        Produto produto = produtoOptional.get();
        if (produto.isVendido()) {
            logger.warn("Produto com ID {} não pode ser desassociado, pois já foi vendido.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        produto.setLeilao(null);
        produtoRepository.save(produto);
        logger.info("Produto com ID {} desassociado do leilão.", produto.getId());
        return ResponseEntity.ok(produto);
    }


    /**
     * Associar um produto a outro leilão.
     *
     * @param id       ID do produto.
     * @param leilaoId ID do leilão ao qual o produto será associado.
     * @return Produto atualizado ou erro.
     */
    @PutMapping("/{id}/associar-leilao/{leilaoId}")
    public ResponseEntity<Produto> associarAOutroLeilao(@PathVariable Long id, @PathVariable Long leilaoId) {
        logger.info("Tentando associar produto com ID {} ao leilão com ID {}", id, leilaoId);
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(leilaoId);
        if (produtoOptional.isEmpty()) {
            logger.warn("Produto com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }
        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão com ID {} não encontrado.", leilaoId);
            return ResponseEntity.notFound().build();
        }
        Produto produto = produtoOptional.get();
        Leilao leilao = leilaoOptional.get();
        if (!produto.getLances().isEmpty()) {
            logger.warn("O produto com ID {} já recebeu lances e não pode ser associado a outro leilão.", id);
            return ResponseEntity.badRequest().build();
        }
        produto.setLeilao(leilao);
        produtoRepository.save(produto);
        logger.info("Produto com ID {} associado ao novo leilão com ID {}.", produto.getId(), leilao.getId());
        return ResponseEntity.ok(produto);
    }


    /**
     * Remover um produto por ID.
     *
     * @param id ID do produto.
     * @return Status HTTP de remoção.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            logger.warn("Produto com ID {} não encontrado para remoção.", id);
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        logger.info("Produto com ID {} removido.", id);
        return ResponseEntity.ok().build();
    }


    /**
     * Busca produtos de um leilão específico aplicando filtros opcionais.
     *
     * @param leilaoId ID do leilão.
     * @param tipoInformatica Tipo de dispositivo de informática (opcional).
     * @param tipoVeiculo  Tipo de veículo (opcional).
     * @param minValor     Valor mínimo do produto (opcional).
     * @param maxValor     Valor máximo do produto (opcional).
     * @param palavraChave Palavra-chave contida no nome do produto (opcional).
     * @return Lista de produtos filtrados.
     */
    @GetMapping("/leiloes/{leilaoId}/produtos")
    public ResponseEntity<List<Produto>> buscarProdutosPorFiltros(
            @PathVariable Long leilaoId,
            @RequestParam(required = false) TipoInformatica tipoInformatica,
            @RequestParam(required = false) TipoVeiculo tipoVeiculo,
            @RequestParam(required = false) Double minValor,
            @RequestParam(required = false) Double maxValor,
            @RequestParam(required = false) String palavraChave) {
        List<Produto> produtos = produtoRepository.findAll().stream()
                .filter(produto -> produto.getLeilao().getId().equals(leilaoId))
                .filter(produto -> tipoInformatica == null || (produto instanceof DispositivoInformatica && ((DispositivoInformatica) produto).getTipoInformatica() == tipoInformatica))
                .filter(produto -> tipoVeiculo == null || (produto instanceof Veiculo && ((Veiculo) produto).getTipoVeiculo() == tipoVeiculo))
                .filter(produto -> minValor == null || produto.getValor() >= minValor)
                .filter(produto -> maxValor == null || produto.getValor() <= maxValor)
                .filter(produto -> palavraChave == null || produto.getNome().contains(palavraChave))
                .collect(Collectors.toList());
        if (produtos.isEmpty()) {
            logger.info("Nenhum produto encontrado para o leilão com ID {}.", leilaoId);
            return ResponseEntity.noContent().build();
        }
        logger.info("Produtos encontrados: {}", produtos.size());
        return ResponseEntity.ok(produtos);
    }


}
