package com.algaworks.algafood.controller;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.service.AtivacaoClienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class MeuPrimeiroController {
  private final AtivacaoClienteService ativacaoClienteService;

  public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
    this.ativacaoClienteService = ativacaoClienteService;
  }

  @GetMapping("/hello")
  @ResponseBody
  public String hello() {
    Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");
    ativacaoClienteService.ativar(joao);
    return "Cliente Ativado!";
  }
}
