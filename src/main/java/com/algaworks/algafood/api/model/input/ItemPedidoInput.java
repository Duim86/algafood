package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoInput {

  @NotNull
  @ApiModelProperty(example = "1", required = true)
  private Long produtoId;

  @NotNull
  @Positive
  @ApiModelProperty(example = "1", required = true)
  private Integer quantidade;

  @ApiModelProperty(example = "Água sem gás")
  private String observacao;

}
