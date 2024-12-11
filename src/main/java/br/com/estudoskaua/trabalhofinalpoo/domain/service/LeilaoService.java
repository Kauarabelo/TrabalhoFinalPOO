package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.exception.LeilaoNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ProdutoNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de criação e associação de leilões e produtos.
 */
@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cria um novo leilão.
     *
     * @param leilao O leilão a ser criado.
     * @return O leilão salvo.
     */
    public Leilao criarLeilao(Leilao leilao) {
        return leilaoRepository.save(leilao);
    }

    /**
     * Associa um produto a um leilão.
     *
     * @param leilaoId O ID do leilão.
     * @param produtoId O ID do produto.
     * @return O leilão ao qual o produto foi associado.
     * @throws LeilaoNaoEncontradoException Se o leilão não for encontrado.
     * @throws ProdutoNaoEncontradoException Se o produto não for encontrado.
     * @throws IllegalStateException Se o leilão não estiver no estado correto para associação.
     */
    public Leilao associarProdutoAoLeilao(Long leilaoId, Long produtoId) {
        // Verifica se o leilão existe
        Leilao leilao = leilaoRepository.findById(leilaoId)
                .orElseThrow(() -> new LeilaoNaoEncontradoException("Leilão não encontrado"));

        // Verifica se o produto existe
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        // Valida o estado do leilão (apenas leilões abertos ou em andamento podem receber produtos)
        if (leilao.getStatus() != Status.ABERTO && leilao.getStatus() != Status.EM_ANDAMENTO) {
            throw new IllegalStateException("Não é possível associar produtos a um leilão fechado");
        }

        // Associa o produto ao leilão
        produto.setLeilao(leilao);
        produtoRepository.save(produto);

        return leilao;
    }
}
