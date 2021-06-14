package com.algaworks.algafood.api;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

  private Long id;
  private String nome;
  // private BigDecimal precoFrete;
  private BigDecimal taxaFrete;
  private CozinhaModel cozinha;
}
