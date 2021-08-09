package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {

  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "Foz do Iguaçu")
  private String nome;

  @ApiModelProperty(example = "Paraná")
  private String estado;
}
