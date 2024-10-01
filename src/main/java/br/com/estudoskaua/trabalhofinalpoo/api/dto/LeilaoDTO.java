package br.com.estudoskaua.trabalhofinalpoo.api.dto;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) para representar um leilão.
 * <p>
 * Este DTO é utilizado para transferir dados entre as camadas da aplicação,
 * incluindo informações necessárias para a criação e validação de leilões.
 * </p>
 *
 * @author Kaua
 */
public class LeilaoDTO {

    @NotNull(message = "Descrição não pode estar vazia")
    @NotEmpty(message = "Descrição não pode estar vazia")
    @Size(max = 255, message = "Descrição não pode exceder 255 caracteres")
    private String descricao; // Descrição do leilão

    @NotNull(message = "IDs dos produtos são obrigatórios")
    @NotEmpty(message = "A lista de IDs dos produtos não pode estar vazia")
    private List<Long> produtoIds; // IDs dos produtos associados ao leilão

    @NotNull(message = "IDs das instituições financeiras são obrigatórios")
    @NotEmpty(message = "A lista de IDs das instituições financeiras não pode estar vazia")
    private List<Long> instituicaoFinanceiraIds; // IDs das instituições financeiras associadas ao leilão

    @NotNull(message = "Data de início é obrigatória")
    @Future(message = "A data de início deve ser uma data futura")
    private LocalDateTime dataInicio; // Data de início do leilão

    @NotNull(message = "Data de término é obrigatória")
    @Future(message = "A data de término deve ser uma data futura")
    private LocalDateTime dataFim; // Data de término do leilão

    @NotNull(message = "Data de visitação é obrigatória")
    @Future(message = "A data de visitação deve ser uma data futura")
    private LocalDateTime dataVisitacao; // Data de visitação do leilão

    @NotNull(message = "Endereço é obrigatório")
    @NotEmpty(message = "Endereço não pode estar vazio")
    private String endereco; // Endereço onde o leilão ocorrerá

    @NotNull(message = "Cidade é obrigatória")
    @NotEmpty(message = "Cidade não pode estar vazia")
    private String cidade; // Cidade onde o leilão ocorrerá

    @NotNull(message = "Estado é obrigatório")
    @NotEmpty(message = "Estado não pode estar vazio")
    private String estado; // Estado onde o leilão ocorrerá

    /**
     * Obtém a descrição do leilão.
     *
     * @return a descrição do leilão.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do leilão.
     *
     * @param descricao a descrição do leilão.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém os IDs dos produtos associados ao leilão.
     *
     * @return a lista de IDs dos produtos.
     */
    public List<Long> getProdutoIds() {
        return produtoIds;
    }

    /**
     * Define os IDs dos produtos associados ao leilão.
     *
     * @param produtoIds a lista de IDs dos produtos.
     */
    public void setProdutoIds(List<Long> produtoIds) {
        this.produtoIds = produtoIds;
    }

    /**
     * Obtém os IDs das instituições financeiras associadas ao leilão.
     *
     * @return a lista de IDs das instituições financeiras.
     */
    public List<Long> getInstituicaoFinanceiraIds() {
        return instituicaoFinanceiraIds;
    }

    /**
     * Define os IDs das instituições financeiras associadas ao leilão.
     *
     * @param instituicaoFinanceiraIds a lista de IDs das instituições financeiras.
     */
    public void setInstituicaoFinanceiraIds(List<Long> instituicaoFinanceiraIds) {
        this.instituicaoFinanceiraIds = instituicaoFinanceiraIds;
    }

    /**
     * Obtém a data de início do leilão.
     *
     * @return a data de início do leilão.
     */
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do leilão.
     *
     * @param dataInicio a data de início do leilão.
     */
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Obtém a data de término do leilão.
     *
     * @return a data de término do leilão.
     */
    public LocalDateTime getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de término do leilão.
     *
     * @param dataFim a data de término do leilão.
     */
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Obtém a data de visitação do leilão.
     *
     * @return a data de visitação do leilão.
     */
    public LocalDateTime getDataVisitacao() {
        return dataVisitacao;
    }

    /**
     * Define a data de visitação do leilão.
     *
     * @param dataVisitacao a data de visitação do leilão.
     */
    public void setDataVisitacao(LocalDateTime dataVisitacao) {
        this.dataVisitacao = dataVisitacao;
    }

    /**
     * Obtém o endereço onde o leilão ocorrerá.
     *
     * @return o endereço do leilão.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço onde o leilão ocorrerá.
     *
     * @param endereco o endereço do leilão.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Obtém a cidade onde o leilão ocorrerá.
     *
     * @return a cidade do leilão.
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade onde o leilão ocorrerá.
     *
     * @param cidade a cidade do leilão.
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Obtém o estado onde o leilão ocorrerá.
     *
     * @return o estado do leilão.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Define o estado onde o leilão ocorrerá.
     *
     * @param estado o estado do leilão.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Define o status do leilão com base nas datas de início e fim.
     * <p>
     * O status será definido como:
     * - ABERTO se a data de início for futura.
     * - FECHADO se a data de fim já tiver passado.
     * - EM_ANDAMENTO se a data de início já tiver passado e a data de fim ainda não.
     * </p>
     *
     * @return o status do leilão.
     */
    public Status definirStatus() {
        LocalDateTime agora = LocalDateTime.now();
        if (dataInicio.isAfter(agora)) {
            return Status.ABERTO; // O leilão ainda não começou
        } else if (dataFim.isBefore(agora)) {
            return Status.FINALIZADO; // O leilão já terminou
        } else {
            return Status.EM_ANDAMENTO; // O leilão está em andamento
        }
    }
}
