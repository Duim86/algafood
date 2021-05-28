package com.algaworks.di.notificacao;

import com.algaworks.di.modelo.Cliente;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {

  @Override
  public void notificar(Cliente cliente, String mensagem) {

    System.out.printf("Notificando %s através do e-mail %s: %s\n",
            cliente.getNome(), cliente.getEmail(), mensagem);
  }
}
