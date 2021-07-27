package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInput {

  @NotBlank
  @ApiModelProperty(value = "Nome do estado", example = "Paraná")
  private String nome;
}
