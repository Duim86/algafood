package com.algaworks.algafood.api.v1.model;

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
public class CidadeModel extends RepresentationModel<CidadeModel> {

  @ApiModelProperty(value = "ID da cidade", example = "1")
  private Long id;

  @ApiModelProperty(example = "Foz do Iguaçu")
  private String nome;

  private EstadoModel estado;

}
