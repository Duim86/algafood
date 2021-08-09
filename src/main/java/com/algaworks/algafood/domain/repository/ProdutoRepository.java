package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

  List<Produto> findAllByRestaurante(Restaurante restaurante);

  List<Produto> findAllByRestauranteAndAtivoTrue(Restaurante restaurante);

  Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

  @Query("select f from FotoProduto f join f.produto p " +
          "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
  Optional<FotoProduto> findFotoPorId(Long restauranteId, Long produtoId);

  @Query("delete from FotoProduto f where f.produto.id = :produtoId")
  void deleteFotoPorId(Long produtoId);
}
