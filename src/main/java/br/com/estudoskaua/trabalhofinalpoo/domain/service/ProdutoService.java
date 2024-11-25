package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Produto produto = criarProdutoBaseadoNoTipo(produtoDTO);
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
        produto.setImagemUrl(produtoDTO.getImagemUrl());
        produto.setLeilao(leilao);
        return produtoRepository.save(produto);
    }

    /**
     * Cria o produto com base no tipo informado no DTO.
     *
     * @param produtoDTO Dados do produto
     * @return O produto criado (Veículo ou Dispositivo)
     */
    private Produto criarProdutoBaseadoNoTipo(ProdutoDTO produtoDTO) {
        if (produtoDTO.getTipoVeiculo() != null && !produtoDTO.getTipoVeiculo().isEmpty()) {
            return criarVeiculo(produtoDTO);
        } else if (produtoDTO.getTipoInformatica() != null && !produtoDTO.getTipoInformatica().isEmpty()) {
            return criarDispositivoInformatica(produtoDTO);
        } else {
            throw new IllegalArgumentException("Tipo de produto inválido.");
        }
    }

    /**
     * Cria um novo veículo com base nos dados do DTO.
     *
     * @param produtoDTO Dados do produto
     * @return O veículo criado
     */
    private Veiculo criarVeiculo(ProdutoDTO produtoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMarca(produtoDTO.getMarca());
        veiculo.setModelo(produtoDTO.getModelo());
        veiculo.setAnoDeFabricacao(produtoDTO.getAnoDeFabricacao());
        veiculo.setTipoVeiculo(TipoVeiculo.valueOf(produtoDTO.getTipoVeiculo()));
        return veiculo;
    }

    /**
     * Cria um novo dispositivo de informática com base nos dados do DTO.
     *
     * @param produtoDTO Dados do produto
     * @return O dispositivo de informática criado
     */
    private DispositivoInformatica criarDispositivoInformatica(ProdutoDTO produtoDTO) {
        DispositivoInformatica dispositivo = new DispositivoInformatica();
        dispositivo.setTipoInformatica(TipoInformatica.valueOf(produtoDTO.getTipoInformatica()));
        return dispositivo;
    }

    /**
     * Desassocia um produto de seu leilão, se não tiver sido vendido.
     *
     * @param produtoId ID do produto a ser desassociado
     * @throws EntityNotFoundException Se o produto não for encontrado
     * @throws IllegalStateException Se o produto já foi vendido
     */
    @Transactional
    public void removerProdutoDoLeilao(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        if (produto.isVendido()) {
            throw new IllegalStateException("Produto vendido, não pode ser desassociado");
        }
        produto.setLeilao(null);
        produtoRepository.save(produto);
    }
}
