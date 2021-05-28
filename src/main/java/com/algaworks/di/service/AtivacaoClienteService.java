package com.algaworks.di.service;

import com.algaworks.di.modelo.Cliente;
import com.algaworks.di.notificacao.Notificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtivacaoClienteService {

//  @Autowired
  private Notificador notificador;

  @Autowired
  public AtivacaoClienteService(Notificador notificador) {
    this.notificador = notificador;
  }

  public AtivacaoClienteService() {
  }

  public void ativar(Cliente cliente) {
    cliente.ativar();

    notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
  }

//  @Autowired
//  public void setNotificador(Notificador notificador) {
//    this.notificador = notificador;
//  }
}
