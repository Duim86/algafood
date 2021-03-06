package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApiV2 {

  private CozinhasEmbeddedModelOpenApiV2 _embedded;
  private Links _links;
  private PageModelOpenApiV2 page;

  @ApiModel("CozinhasEmbeddedModelOpenApi")
  @Data
  public static class CozinhasEmbeddedModelOpenApiV2 {
    private List<CozinhaModelV2> cozinhas;
  }

}
