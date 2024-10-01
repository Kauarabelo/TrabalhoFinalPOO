package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Leilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * LeilaoRepository
 *
 * @author kaua
 */
@Repository
public interface LeilaoRepository extends JpaRepository<Leilao, Long> {

}
