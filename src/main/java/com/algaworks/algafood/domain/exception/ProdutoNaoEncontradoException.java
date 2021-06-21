package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public ProdutoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
    this("Não existe um cadastro de produto com código " + produtoId + " para o restaurante de código " + restauranteId);
  }
}
