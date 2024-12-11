package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import br.com.estudoskaua.trabalhofinalpoo.domain.model.veiculos.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Produto.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para operações CRUD padrão e funcionalidades de acesso a dados
 * para a entidade {@link Produto}. O identificador da entidade é do tipo
 * {@link Long}.
 * </p>
 *
 * @author Kaua
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
