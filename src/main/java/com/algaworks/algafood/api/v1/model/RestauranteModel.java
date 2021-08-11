package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;


@Relation("restaurantes")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "Thai Food")
  private String nome;

  @ApiModelProperty(example = "10.05")
  private BigDecimal taxaFrete;

  private CozinhaModel cozinha;

  @ApiModelProperty(example = "true")
  private Boolean ativo;

  @ApiModelProperty(example = "true")
  private Boolean aberto;

  private EnderecoModel endereco;
}
