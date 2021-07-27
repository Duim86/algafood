package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel {

  @ApiModelProperty(example = "1")
  private Long produtoId;

  @ApiModelProperty(example = "Água")
  private String produtoNome;

  @ApiModelProperty(example = "3")
  private Integer quantidade;

  @ApiModelProperty(example = "3.00")
  private BigDecimal precoUnitario;

  @ApiModelProperty(example = "9.00")
  private BigDecimal precoTotal;

  @ApiModelProperty(example = "Água com gelo e limão")
  private String observacao;
}