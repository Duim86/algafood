package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

  @ApiModelProperty(example = "1")
  @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
  private Long id;

  @ApiModelProperty(example = "Thai Food")
  @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
  private String nome;

  @ApiModelProperty(example = "10.05")
  @JsonView(RestauranteView.Resumo.class)
  private BigDecimal taxaFrete;

  @JsonView(RestauranteView.Resumo.class)
  private CozinhaModel cozinha;

  @ApiModelProperty(example = "true")
  private Boolean ativo;

  @ApiModelProperty(example = "true")
  private Boolean aberto;

  private EnderecoModel endereco;
}
