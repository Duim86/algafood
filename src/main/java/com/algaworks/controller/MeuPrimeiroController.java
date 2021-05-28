package com.algaworks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class MeuPrimeiroController {

  @GetMapping
  public String hello() {
    return "hello";
  }
}
