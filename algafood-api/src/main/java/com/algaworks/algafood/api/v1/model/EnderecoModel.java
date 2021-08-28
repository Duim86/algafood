package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

  @ApiModelProperty(example = "85800-210")
  private String cep;

  @ApiModelProperty(example = "Avenida das Cataratas")
  private String logradouro;

  @ApiModelProperty(example = "\"1500\"")
  private String numero;

  @ApiModelProperty(example = "Uniamérica")
  private String complemento;

  @ApiModelProperty(example = "Vila Yolanda")
  private String bairro;


  private CidadeResumoModel cidade;
}
