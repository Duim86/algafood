package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "CONSULTAR_COZINHAS")
  private String nome;

  @ApiModelProperty(example = "Permite consultar cozinhas")
  private String descricao;
}
