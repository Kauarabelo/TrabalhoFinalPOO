package br.com.estudoskaua.trabalhofinalpoo.api.controller;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para gerenciar produtos em leilão.
 */
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    private final ProdutoRepository produtoRepository;
    private final LeilaoRepository leilaoRepository;

    /**
     * Listar todos os produtos cadastrados.
     *
     * @return Lista de produtos.
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAllProdutosComLances();
        logger.info("Listando todos os produtos. Total: {}", produtos.size());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
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
     */
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(produtoDTO.getLeilaoId());
        if (leilaoOptional.isEmpty()) {
            logger.warn("Leilão com ID {} não encontrado para vinculação.", produtoDTO.getLeilaoId());
            return ResponseEntity.badRequest().build();
        }

        Produto produto = criarProdutoBaseadoEmDTO(produtoDTO, leilaoOptional.get());
        Produto produtoSalvo = produtoRepository.save(produto);

        logger.info("Produto criado com sucesso: {}", produtoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    /**
     * Criar produto com base nos dados do DTO e no leilão associado.
     *
     * @param produtoDTO Dados do produto.
     * @param leilao     Leilão associado.
     * @return Instância do produto.
     */
    private Produto criarProdutoBaseadoEmDTO(ProdutoDTO produtoDTO, Leilao leilao) {
        try {
            if (produtoDTO.getTipoInformatica() != null) {
                // Remove espaços e converte para maiúsculas antes de passar para valueOf
                String tipoInformaticaString = produtoDTO.getTipoInformatica().trim().toUpperCase();
                TipoInformatica tipoInformatica = TipoInformatica.valueOf(tipoInformaticaString);
                return new DispositivoInformatica(
                        produtoDTO.getNome(),
                        produtoDTO.getDescricao(),
                        produtoDTO.getValor(),
                        produtoDTO.getImagemUrl(),
                        leilao,  // Aqui passamos o leilão diretamente
                        tipoInformatica,
                        produtoDTO.getEspecificacoes(),
                        produtoDTO.getMarca(),
                        produtoDTO.getModelo()
                );

            } else if (produtoDTO.getTipoVeiculo() != null) {
                // Similar para TipoVeiculo
                String tipoVeiculoString = produtoDTO.getTipoVeiculo().trim().toUpperCase();
                TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(tipoVeiculoString);
                return new Veiculo(
                        produtoDTO.getNome(),
                        produtoDTO.getDescricao(),
                        produtoDTO.getValor(),
                        produtoDTO.getImagemUrl(),
                        leilao,  // Aqui passamos o leilão diretamente
                        produtoDTO.getMarca(),
                        produtoDTO.getModelo(),
                        produtoDTO.getAnoDeFabricacao(),
                        tipoVeiculo
                );
            }

            // Se nenhum tipo for encontrado, lançar exceção
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de produto inválido: nenhum tipo de produto encontrado.");

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao converter tipo de produto: {}", e.getMessage());
            // Retorne uma resposta de erro adequada
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de produto inválido", e);
        }
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
            logger.warn("Produto com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        }

        Produto produto = produtoOptional.get();
        if (produto.isVendido()) {
            logger.warn("Produto com ID {} já vendido e não pode ser desassociado.", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        produto.setLeilao(null);
        produtoRepository.save(produto);
        logger.info("Produto com ID {} desassociado com sucesso.", id);
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
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        Optional<Leilao> leilaoOptional = leilaoRepository.findById(leilaoId);

        if (produtoOptional.isEmpty() || leilaoOptional.isEmpty()) {
            logger.warn("Produto ou Leilão não encontrado. Produto ID: {}, Leilão ID: {}", id, leilaoId);
            return ResponseEntity.notFound().build();
        }

        Produto produto = produtoOptional.get();
        if (!produto.getLances().isEmpty()) {
            logger.warn("Produto com ID {} já recebeu lances e não pode ser reassociado.", id);
            return ResponseEntity.badRequest().build();
        }

        produto.setLeilao(leilaoOptional.get());
        produtoRepository.save(produto);
        logger.info("Produto com ID {} associado ao leilão com ID {}.", id, leilaoId);
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
        logger.info("Produto com ID {} removido com sucesso.", id);
        return ResponseEntity.ok().build();
    }
}
