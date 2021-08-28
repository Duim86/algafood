package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

  @Autowired
  private AlgaLinks algaLinks;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private FormaDePagamentoModelAssembler formaDePagamentoModelAssembler;

  @Override
  @GetMapping
  public CollectionModel<FormaDePagamentoModel> listar(@PathVariable Long restauranteId) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    var formasDePagamentoModel = formaDePagamentoModelAssembler.toCollectionModel(restaurante.getFormasDePagamento())
            .removeLinks()
            .add(algaLinks.linkToRestauranteFormasDePagamento(restauranteId))
            .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

    formasDePagamentoModel.getContent()
            .forEach(formaDePagamentoModel -> formaDePagamentoModel.add(
                    algaLinks.linkToRestauranteFormaPagamentoDesassociacao(restauranteId, formaDePagamentoModel.getId(), "desassociar")));
    return formasDePagamentoModel;
  }

  @Override
  @PutMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
    return ResponseEntity.noContent().build();
  }

  @Override
  @DeleteMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

    return ResponseEntity.noContent().build();
  }
}
