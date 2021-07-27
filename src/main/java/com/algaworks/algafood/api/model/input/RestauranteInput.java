package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

  @NotBlank
  @ApiModelProperty(example = "Thai Food", required = true)
  private String nome;

  @PositiveOrZero
  @ApiModelProperty(example = "5.99", required = true)
  @NotNull
  private BigDecimal taxaFrete;

  @Valid
  @NotNull
  private CozinhaIdInput cozinha;

  @Valid
  @NotNull
  private EnderecoInput endereco;
}
