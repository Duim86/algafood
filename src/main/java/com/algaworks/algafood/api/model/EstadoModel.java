package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel {

  @ApiModelProperty(value = "ID da cidade", example = "1")
  private Long id;

  @ApiModelProperty(value = "Nome do estado", example = "Paraná")
  private String nome;
}
