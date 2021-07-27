package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoModel {

  @ApiModelProperty(value = "ID do produto", example = "1")
  private Long id;

  @ApiModelProperty(example = "Água")
  private String nome;

  @ApiModelProperty(example = "Água com gás")
  private String descricao;

  @ApiModelProperty(example = "3.99")
  private BigDecimal preco;

  @ApiModelProperty(example = "true")
  private boolean ativo;
}
