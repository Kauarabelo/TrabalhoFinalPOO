package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProdutoRepository
 *
 * @author kaua
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
