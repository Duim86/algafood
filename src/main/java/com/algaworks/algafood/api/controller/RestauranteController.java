package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.api.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;


  @Autowired
  private RestauranteModelAssembler restauranteModelAssembler;

  @Autowired
  private RestauranteInputDisassembler restauranteInputDisassembler;

  @Override
  @GetMapping
  public CollectionModel<RestauranteModel> listar() {
    return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
  }

  @Override
  @JsonView(RestauranteView.ApenasNome.class)
  @GetMapping(params = "projecao=apenas-nome")
  public CollectionModel<RestauranteModel> listarApenasNome() {
    return listar();
  }


  @Override
  @GetMapping("/{restauranteId}")
  public RestauranteModel buscar(@PathVariable Long restauranteId) {

    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return restauranteModelAssembler.toModel(restaurante);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestauranteModel adicionar(@RequestBody
                                    @Valid RestauranteInput restauranteInput) {
    try {
      var restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
      return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @Override
  @PutMapping("/{restauranteId}")
  public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInput restauranteInput) {
    var restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

    restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

    try {
      return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{restauranteId}")
  public void remover(@PathVariable Long restauranteId) {

    cadastroRestaurante.excluir(restauranteId);
  }

  @Override
  @PutMapping("/{restauranteId}/ativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.ativar(restauranteId);
  }

  @Override
  @DeleteMapping("/{restauranteId}/inativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void inativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.inativar(restauranteId);
  }


  @Override
  @PutMapping("/ativacoes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ativarEmMassa(@RequestBody List<Long> restauranteIds) {
    try {
      cadastroRestaurante.ativar(restauranteIds);
    } catch (RestauranteNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @Override
  @DeleteMapping("/ativacoes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void inativarEmMassa(@RequestBody List<Long> restauranteIds) {
    try {
      cadastroRestaurante.inativar(restauranteIds);
    } catch (RestauranteNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }


  @Override
  @PutMapping("/{restauranteId}/abertura")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void abrir(@PathVariable Long restauranteId) {
    cadastroRestaurante.abrir(restauranteId);
  }

  @Override
  @PutMapping("/{restauranteId}/fechamento")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void fechar(@PathVariable Long restauranteId) {
    cadastroRestaurante.fechar(restauranteId);
  }

}
