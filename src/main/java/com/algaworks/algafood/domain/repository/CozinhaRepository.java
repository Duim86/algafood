package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
  List<Cozinha> findByNomeContaining(String nome);

  boolean existsByNome(String nome);
}
