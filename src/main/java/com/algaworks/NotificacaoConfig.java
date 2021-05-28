package com.algaworks;

import com.algaworks.di.notificacao.Notificador;
import com.algaworks.di.notificacao.NotificadorEmail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoConfig {

  @Bean
  public NotificadorEmail notificadorEmail() {
    NotificadorEmail notificador = new NotificadorEmail("smtp.algamail.com.br");
    notificador.setCaixaAlta(true);

    return notificador;
  }

}
