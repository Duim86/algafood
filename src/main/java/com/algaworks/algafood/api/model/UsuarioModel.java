package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Relation(collectionRelation = "usuarios")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "João da Silva")
  private String nome;

  @ApiModelProperty(example = "joaosilva@gmail.com")
  private String email;
}
