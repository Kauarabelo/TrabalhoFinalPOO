package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Lance.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para operações CRUD padrão e funcionalidades de acesso a dados
 * para a entidade {@link Lance}. O identificador da entidade é do tipo
 * {@link Long}.
 * </p>
 *
 * @author Kaua
 */
@Repository
public interface LanceRepository extends JpaRepository<Lance, Long> {
}
