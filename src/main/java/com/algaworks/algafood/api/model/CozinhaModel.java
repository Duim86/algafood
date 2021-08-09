package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
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
  @JsonView(RestauranteView.Resumo.class)
  private Long id;

  @ApiModelProperty(example = "Italiana")
  @JsonView(RestauranteView.Resumo.class)
  private String nome;
}
