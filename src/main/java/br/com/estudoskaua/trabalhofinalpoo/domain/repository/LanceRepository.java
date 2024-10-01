package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Lance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * LanceRepository
 *
 * @author kaua
 */
@Repository
public interface LanceRepository extends JpaRepository<Lance, Long> {

}
