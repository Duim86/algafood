package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
  @ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
  private final OffsetDateTime timestamp;

  @ApiModelProperty(example = "400", position = 1)
  private final Integer status;

  @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
  private final String type;

  @ApiModelProperty(example = "Dados invalidos", position = 15)
  private final String title;

  @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 20)
  private final String detail;

  @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 25)
  private final String userMessage;

  @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro", position = 30)
  private final List<Object> objects;

  @ApiModel("ObjetoProblema")
  @Getter
  @Builder
  public static class Object {

    @ApiModelProperty(example = "preco")
    private final String name;

    @ApiModelProperty(example = "O preco e obrigatorio")
    private final String userMessage;
  }
}
