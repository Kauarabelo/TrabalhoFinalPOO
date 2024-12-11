package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.ProdutoDTO;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ProdutoNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ProdutoVendidoException;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.dispositivosInformatica.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos.*;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LeilaoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
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

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Produto getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado."));
    }

    @Transactional
    public Produto updateProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = getProdutoById(id);

        produtoExistente.setNome(produtoDTO.getNome());
        produtoExistente.setDescricao(produtoDTO.getDescricao());
        produtoExistente.setValor(produtoDTO.getValor());
        produtoExistente.setImagemUrl(produtoDTO.getImagemUrl());
        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void deleteProduto(Long id) {
        Produto produto = getProdutoById(id);
        produtoRepository.delete(produto);
    }

    /**
     * Cria um novo produto associado a um leilão.
     *
     * @param produtoDTO Dados do produto a ser criado
     * @return O produto criado
     * @throws ProdutoNaoEncontradoException Se o leilão não for encontrado
     */
    @Transactional
    public Produto criarProduto(ProdutoDTO produtoDTO) {
        Leilao leilao = leilaoRepository.findById(produtoDTO.getLeilaoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Leilão não encontrado"));

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
     * @throws IllegalArgumentException Se o tipo de produto for inválido
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
        Veiculo veiculo;
        switch (produtoDTO.getTipoVeiculo()) {
            case "CARRO":
                veiculo = new Carro();
                break;
            case "CAMINHAO":
                veiculo = new Caminhao();
                break;
            case "MOTOCICLETA":
                veiculo = new Motocicleta();
                break;
            case "UTILITARIO":
                veiculo = new Utilitario();
                break;
            default:
                throw new IllegalArgumentException("Tipo de veículo inválido.");
        }
        veiculo.setMarca(produtoDTO.getMarca());
        veiculo.setModelo(produtoDTO.getModelo());
        veiculo.setAnoDeFabricacao(produtoDTO.getAnoDeFabricacao());
        veiculo.setPlaca(produtoDTO.getPlaca());
        return veiculo;
    }

    /**
     * Cria um novo dispositivo de informática com base nos dados do DTO.
     *
     * @param produtoDTO Dados do produto
     * @return O dispositivo de informática criado
     */
    private DispositivoInformatica criarDispositivoInformatica(ProdutoDTO produtoDTO) {
        DispositivoInformatica dispositivo;
        switch (produtoDTO.getTipoInformatica()) {
            case "NOTEBOOK":
                dispositivo = new Notebook();
                break;
            case "IMPRESSORA":
                dispositivo = new Impressora();
                break;
            case "MONITOR":
                dispositivo = new Monitor();
                break;
            case "HUB":
                dispositivo = new Hub();
                break;
            case "SWITCH":
                dispositivo = new Switch();
                break;
            case "ROTEADOR":
                dispositivo = new Roteador();
                break;
            default:
                throw new IllegalArgumentException("Tipo de dispositivo inválido.");
        }
        dispositivo.setMarca(produtoDTO.getMarca());
        dispositivo.setModelo(produtoDTO.getModelo());
        dispositivo.setEspecificacoes(produtoDTO.getEspecificacoes());
        return dispositivo;
    }


    /**
     * Desassocia um produto de seu leilão, se não tiver sido vendido.
     *
     * @param produtoId ID do produto a ser desassociado
     * @throws ProdutoNaoEncontradoException Se o produto não for encontrado
     * @throws ProdutoVendidoException Se o produto já foi vendido
     */
    @Transactional
    public void removerProdutoDoLeilao(Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        if (produto.isVendido()) {
            throw new ProdutoVendidoException("Produto vendido, não pode ser desassociado.");
        }

        produto.setLeilao(null);
        produtoRepository.save(produto);
    }
}
