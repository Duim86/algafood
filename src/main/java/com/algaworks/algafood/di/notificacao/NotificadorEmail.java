package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@TipoDoNotificador(NivelUrgencia.SEM_PRIORIDADE)
@Component
public class NotificadorEmail implements Notificador {

  @Autowired
  private NotificadorProperties properties;

  @Override
  public void notificar(Cliente cliente, String mensagem) {

    System.out.println("host: "+properties.getHostServidor());
    System.out.println("porta: "+properties.getPortaServidor());


    System.out.printf("Notificando %s através do e-mail %s: %s\n",
            cliente.getNome(), cliente.getEmail(), mensagem);
  }
}
