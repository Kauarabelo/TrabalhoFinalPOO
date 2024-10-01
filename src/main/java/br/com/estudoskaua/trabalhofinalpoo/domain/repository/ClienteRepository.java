package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Cliente.
 * <p>
 * Esta interface extende {@link JpaRepository}, fornecendo métodos
 * para operações CRUD padrão e funcionalidades de acesso a dados
 * para a entidade {@link Cliente}. O identificador da entidade é do tipo {@link Long}.
 * </p>
 *
 * @author Kaua
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
