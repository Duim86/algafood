package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation("items-pedidos")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

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