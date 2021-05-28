//package com.algaworks;
//
//import com.algaworks.di.notificacao.NotificadorEmail;
//import com.algaworks.di.service.AtivacaoClienteService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
//public class AlgaConfig {
//  @Bean
//  public NotificadorEmail notificadorEmail(){
//    NotificadorEmail notificador = new NotificadorEmail("smtp.algamail.com.br");
//    notificador.setCaixaAlta(true);
//    return notificador;
//  }
//
//  @Bean
//  public AtivacaoClienteService ativacaoClienteService(){
//    return new AtivacaoClienteService(notificadorEmail());
//  }
//}
