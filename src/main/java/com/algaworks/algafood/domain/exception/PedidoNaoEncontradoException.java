package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public PedidoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public PedidoNaoEncontradoException(Long pedidoId) {
    this("Não existe um cadastro de pedido com código " + pedidoId);
  }
}
