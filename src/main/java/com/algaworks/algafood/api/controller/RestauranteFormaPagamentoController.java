package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.FormaDePagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private FormaDePagamentoModelAssembler formaDePagamentoModelAssembler;

  @GetMapping
  public List<FormaDePagamentoModel> listar(@PathVariable Long restauranteId) {
    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return formaDePagamentoModelAssembler.toCollectionModel(restaurante.getFormasDePagamento());
  }

  @PutMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId ) {
    cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
  }

  @DeleteMapping("/{formaPagamentoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId ) {
    cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
  }
}
