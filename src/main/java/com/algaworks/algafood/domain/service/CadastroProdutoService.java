package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CadastroProdutoService {
  @Autowired
  private ProdutoRepository produtoRepository;

  @Transactional
  public Produto salvar(Produto produto) {
    return produtoRepository.save(produto);
  }

  public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
    return produtoRepository.findByIdAndRestauranteId(restauranteId, produtoId).
            orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
  }
}
