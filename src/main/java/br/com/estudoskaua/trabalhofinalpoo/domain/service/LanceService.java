package br.com.estudoskaua.trabalhofinalpoo.domain.service;

import br.com.estudoskaua.trabalhofinalpoo.api.dto.LanceDTO;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ClienteNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.ProdutoNaoEncontradoException;
import br.com.estudoskaua.trabalhofinalpoo.api.exception.LanceValorInvalidoException;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ClienteRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.ProdutoRepository;
import br.com.estudoskaua.trabalhofinalpoo.domain.repository.LanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanceService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LanceRepository lanceRepository;

    /**
     * Processa um lance em um produto no leilão.
     *
     * @param lanceDTO Dados do lance a ser processado
     * @return O lance criado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws ProdutoNaoEncontradoException Se o produto não for encontrado
     * @throws LanceValorInvalidoException Se o valor do lance for inválido
     */
    public Lance processarLance(LanceDTO lanceDTO) {
        // Verifica se o cliente existe
        Cliente cliente = clienteRepository.findById(lanceDTO.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        // Verifica se o produto existe
        Produto produto = produtoRepository.findById(lanceDTO.getProdutoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        // Valida o valor do lance
        if (lanceDTO.getValor() <= produto.getValor()) {
            throw new LanceValorInvalidoException("O valor do lance deve ser maior que o valor inicial do produto.");
        }

        // Cria o lance
        Lance lance = new Lance();
        lance.setCliente(cliente);
        lance.setProduto(produto);
        lance.setValor(lanceDTO.getValor());
        lance.setDataLance(lanceDTO.getDataLance());

        // Salva o lance
        return lanceRepository.save(lance);
    }
}
