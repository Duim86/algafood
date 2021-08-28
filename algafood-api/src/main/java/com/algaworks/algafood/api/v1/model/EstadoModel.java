package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class EstadoModel extends RepresentationModel<EstadoModel> {

  @ApiModelProperty(value = "ID da cidade", example = "1")
  private Long id;

  @ApiModelProperty(value = "Nome do estado", example = "Paraná")
  private String nome;
}
