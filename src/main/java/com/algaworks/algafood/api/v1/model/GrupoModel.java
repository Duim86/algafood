package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class GrupoModel extends RepresentationModel<GrupoModel> {

  @ApiModelProperty(value = "ID do grupo", example = "1")
  private Long id;

  @ApiModelProperty(example = "Gerente")
  private String nome;
}
