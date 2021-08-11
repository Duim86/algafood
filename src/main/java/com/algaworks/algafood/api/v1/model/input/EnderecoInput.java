package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EnderecoInput {

  @NotBlank
  @ApiModelProperty(example = "85800-210", required = true)
  private String cep;

  @NotBlank
  @ApiModelProperty(example = "Avenida das Cataratas", required = true)
  private String logradouro;

  @NotBlank
  @ApiModelProperty(example = "1555", required = true)
  private String numero;

  @ApiModelProperty(example = "Uniamérica")
  private String complemento;

  @NotBlank
  @ApiModelProperty(example = "Vila Yolanda", required = true)
  private String bairro;

  @Valid
  @NotNull
  private CidadeIdInput cidade;
}
