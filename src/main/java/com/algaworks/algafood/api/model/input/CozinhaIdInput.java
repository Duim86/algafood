package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdInput {

  @NotNull
  @ApiModelProperty(value = "ID da cozinha", example = "1", required = true)
  private Long id;
}
