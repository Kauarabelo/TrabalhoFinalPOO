package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClienteRepository
 *
 * @author kaua
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
