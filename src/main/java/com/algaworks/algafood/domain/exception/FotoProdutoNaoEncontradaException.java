package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public FotoProdutoNaoEncontradaException(String mensagem) {
    super(mensagem);
  }

  public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
    this("Não existe um cadastro de foto do produto com código " + produtoId + " para o restaurante de código " + restauranteId);
  }
}
