package com.algaworks.algafood.domain.exception;

public class FormaDePagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {
  public static final long serialVersionUID = 1L;

  public FormaDePagamentoNaoEncontradoException(String mensagem) {
    super(mensagem);
  }

  public FormaDePagamentoNaoEncontradoException(Long formaDePagamentoId) {
    this("Não existe um cadastro de forma de pagamento com código " + formaDePagamentoId);
  }
}
