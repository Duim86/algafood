package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

  @ApiModelProperty(value = "ID do produto", example = "1")
  private Long id;

  @ApiModelProperty(example = "Água")
  private String nome;

  @ApiModelProperty(example = "Água com gás")
  private String descricao;

  @ApiModelProperty(example = "3.99")
  private BigDecimal preco;

  @ApiModelProperty(example = "true")
  private boolean ativo;
}
