package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "Italiana")
  private String nome;
}
