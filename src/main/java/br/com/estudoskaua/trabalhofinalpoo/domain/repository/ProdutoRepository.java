package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.lances")
    List<Produto> findAllProdutosComLances();

    @Query("SELECT p FROM Produto p LEFT JOIN FETCH p.lances WHERE p.id = :id")
    Optional<Produto> findByIdComLances(@Param("id") Long id);

    List<Produto> findAllById(Iterable<Long> ids);

    // Ajuste do método para buscar por ID de Leilão e Status
    List<Produto> findByLeilaoIdAndLeilaoStatus(Long leilaoId, Status status);

}

