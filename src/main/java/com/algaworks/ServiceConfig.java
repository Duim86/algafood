package com.algaworks;

import com.algaworks.di.notificacao.Notificador;
import com.algaworks.di.notificacao.NotificadorEmail;
import com.algaworks.di.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Bean
  public AtivacaoClienteService ativacaoClienteService(Notificador notificador) {
    return new AtivacaoClienteService(notificador);
  }
}
