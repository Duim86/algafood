package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public PedidoNaoEncontradoException(String codigoPedido) {
    super("Não existe um cadastro de pedido com código " + codigoPedido);
  }
}
