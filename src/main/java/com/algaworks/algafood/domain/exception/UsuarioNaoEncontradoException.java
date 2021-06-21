package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public UsuarioNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public UsuarioNaoEncontradoException(Long grupoId) {
    this("Não existe um usuario de grupo com código " + grupoId);
  }
}
