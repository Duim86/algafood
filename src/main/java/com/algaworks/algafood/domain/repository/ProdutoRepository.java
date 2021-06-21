package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

  List<Produto> findAllByRestaurante(Restaurante restaurante);

  Optional<Produto> findByIdAndRestauranteId(Long restauranteId, Long produtoId);
}
