package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public CozinhaNaoEncontradaException(String mensagem) {
    super(mensagem);
  }

  public CozinhaNaoEncontradaException(Long estadoId) {
    this("Não existe um cadastro de cozinha com código " + estadoId);
  }
}
