package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoModel {

  @ApiModelProperty(example = "b172992b-37a7-4a07-8e06-cf230bcb186a")
  private String codigo;

  @ApiModelProperty(example = "298.90")
  private BigDecimal subtotal;

  @ApiModelProperty(example = "10.00")
  private BigDecimal taxaFrete;

  @ApiModelProperty(example = "308.90")
  private BigDecimal valorTotal;

  @ApiModelProperty(example = "CRIADO")
  private String status;

  @ApiModelProperty(example = "2021-06-21T21:19:38Z")
  private OffsetDateTime dataCriacao;

  private RestauranteResumoModel restaurante;
  private UsuarioModel cliente;
}