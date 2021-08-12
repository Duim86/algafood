package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApiV2 {

 private CidadeEmbeddedModelOpenApiV2 _embedded;
 private Links _links;

  @ApiModel("CidadesEmbeddedModelOpenApi")
  @Data
  public static class CidadeEmbeddedModelOpenApiV2 {
    private List<CidadeModelV2> cidades;
  }
}
