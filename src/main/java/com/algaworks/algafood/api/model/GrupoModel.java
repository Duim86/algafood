package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoModel {

  @ApiModelProperty(value = "ID do grupo", example = "1")
  private Long id;

  @ApiModelProperty(example = "Gerente")
  private String nome;
}
