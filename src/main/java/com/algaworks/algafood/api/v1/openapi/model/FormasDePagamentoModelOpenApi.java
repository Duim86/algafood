package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasDePagamentoModel")
@Data
public class FormasDePagamentoModelOpenApi {

  private FormasPagamentoEmbeddedModelOpenApi _embedded;
  private Links _links;

  @ApiModel("FormasPagamentoEmbeddedModel")
  @Data
  public static class FormasPagamentoEmbeddedModelOpenApi {

    private List<FormaDePagamentoModel> formasPagamento;

  }
}
