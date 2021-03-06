package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository
        extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

  @Query("from Restaurante r join fetch r.cozinha order by r.id")
  List<Restaurante> findAll();

  List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
  // find/ query/ read/ get / stream

  @Query("from Restaurante where nome like %:nome% and cozinha.id =:id")
  List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

  List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

  Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

  List<Restaurante> findTop2ByNomeContaining(String nome);

  int countByCozinhaId(Long cozinha);
}
