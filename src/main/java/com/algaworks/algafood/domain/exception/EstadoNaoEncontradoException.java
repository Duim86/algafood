package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public EstadoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public EstadoNaoEncontradoException(Long estadoId) {
    this("Não existe um cadastro de estado com código " + estadoId);
  }
}
