package com.algaworks.di.service;

import com.algaworks.di.modelo.Cliente;
import com.algaworks.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

  private Notificador notificador;

  @Autowired(required = false)
  public AtivacaoClienteService(Notificador notificador) {
    this.notificador = notificador;
  }

  public AtivacaoClienteService() {
  }

  public void ativar(Cliente cliente) {
    cliente.ativar();

    if (notificador != null) {
      notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
    } else {
      System.out.println("Não existe notificador, mas cliente foi ativado.");
    }
  }
}
