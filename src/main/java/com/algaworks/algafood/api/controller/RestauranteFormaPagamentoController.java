package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private FormaDePagamentoModelAssembler formaDePagamentoModelAssembler;

  @Override
  @GetMapping
  public List<FormaDePagamentoModel> listar(@PathVariable Long restauranteId) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return formaDePagamentoModelAssembler.toCollectionModel(restaurante.getFormasDePagamento());
  }

  @Override
  @PutMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
  }

  @Override
  @DeleteMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
    cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
  }
}
