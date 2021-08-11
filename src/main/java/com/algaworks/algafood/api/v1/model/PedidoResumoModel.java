package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

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

  private RestauranteApenasNomeModel restaurante;
  private UsuarioModel cliente;
}