package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public RestauranteNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public RestauranteNaoEncontradoException(Long estadoId) {
    this("Não existe um cadastro de restaurante com código " + estadoId);
  }
}
