package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public CidadeNaoEncontradaException(String mensagem) {
    super(mensagem);
  }

  public CidadeNaoEncontradaException(Long estadoId) {
    this("Não existe um cadastro de cidade com código " + estadoId);
  }
}
