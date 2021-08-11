package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

  @NotBlank
  @ApiModelProperty(example = "Coca-cola", required = true)
  private String nome;

  @NotBlank
  @ApiModelProperty(example = "Coca-cola zero", required = true)
  private String descricao;

  @NotNull
  @PositiveOrZero
  @ApiModelProperty(example = "5.99", required = true)
  private BigDecimal preco;

  @NotNull
  @ApiModelProperty(example = "true", required = true)
  private boolean ativo;
}