package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formas-de-pagamento")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class FormaDePagamentoModel extends RepresentationModel<FormaDePagamentoModel> {
  @ApiModelProperty(example = "1")
  private Long id;

  @ApiModelProperty(example = "Cartão de Crédito")
  private String descricao;
}
