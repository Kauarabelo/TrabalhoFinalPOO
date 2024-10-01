package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela gestão dos produtos no sistema de leilão.
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LeilaoRepository leilaoRepository;

    /**
     * Cria um novo produto associado a um leilão.
     *
     * @param produtoDTO Dados do produto a ser criado
     * @return O produto criado
     * @throws EntityNotFoundException Se o leilão não for encontrado
     */
    @Transactional
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        Leilao leilao = leilaoRepository.findById(produtoDTO.getLeilaoId())
                .orElseThrow(() -> new EntityNotFoundException("Leilão não encontrado"));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
        produto.setImagemUrl(produtoDTO.getImagemUrl());
        produto.setLeilao(leilao);

        return produtoRepository.save(produto);
    }

    /**
     * Desassocia um produto de seu leilão, se não tiver sido vendido.
     *
     * @param produtoId ID do produto a ser desassociado
     * @throws EntityNotFoundException Se o produto não for encontrado
     * @throws IllegalStateException Se o produto já foi vendido
     */
    @Transactional
    public void desassociarProduto(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        if (produto.isVendido()) {
            throw new IllegalStateException("Produto vendido, não pode ser desassociado");
        }
        produto.setLeilao(null);
        produtoRepository.save(produto);
    }
}
