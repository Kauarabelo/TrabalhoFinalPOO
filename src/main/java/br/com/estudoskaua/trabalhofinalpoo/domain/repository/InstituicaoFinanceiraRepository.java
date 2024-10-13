package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade InstituicaoFinanceira.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para operações CRUD padrão e funcionalidades de acesso a dados
 * para a entidade {@link InstituicaoFinanceira}. O identificador
 * da entidade é do tipo {@link Long}.
 * </p>
 *
 * @author Kaua
 */
@Repository
public interface InstituicaoFinanceiraRepository extends JpaRepository<InstituicaoFinanceira, Long> {
    List<InstituicaoFinanceira> findAllById(Iterable<Long> ids);

}
