package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade Leilão.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para operações CRUD padrão e funcionalidades de acesso a dados
 * para a entidade {@link Leilao}. O identificador da entidade é do tipo
 * {@link Long}.
 * </p>
 *
 * @author Kaua
 */
@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long> {
    List<Leilao> findByCliente_Id(Long clienteId);  // Alterar aqui
}

