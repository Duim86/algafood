package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ApiModel("CidadeModel")
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

  @ApiModelProperty(value = "ID da cidade", example = "1")
  private Long idCidade;

  @ApiModelProperty(example = "Foz do Iguaçu")
  private String nomeCidade;

  @ApiModelProperty(example = "1")
  private Long idEstado;

  @ApiModelProperty(example = "Paraná")
  private String nomeEstado;

}
