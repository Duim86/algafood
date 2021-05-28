package com.algaworks.di.notificacao;

import com.algaworks.di.modelo.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmailMock implements Notificador {

  public NotificadorEmailMock(){
    System.out.println("NotificadorEmail MOCK");
  }

  @Override
  public void notificar(Cliente cliente, String mensagem) {

    System.out.printf("Mock: Notificando %s através do e-mail %s: %s\n",
            cliente.getNome(), cliente.getEmail(), mensagem);
  }
}
