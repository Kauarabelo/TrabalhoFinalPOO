package br.com.estudoskaua.trabalhofinalpoo.domain.repository;

import br.com.estudoskaua.trabalhofinalpoo.domain.model.InstituicaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoFinanceiraRepository extends JpaRepository<InstituicaoFinanceira, Long> {
}
