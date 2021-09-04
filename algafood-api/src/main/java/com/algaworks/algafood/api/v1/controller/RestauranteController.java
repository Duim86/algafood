package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.algaworks.algafood.core.security.CheckSecurity.Restaurantes;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;


  @Autowired
  private RestauranteModelAssembler restauranteModelAssembler;

  @Autowired
  private RestauranteInputDisassembler restauranteInputDisassembler;

  @Autowired
  private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

  @Autowired
  private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

  @Restaurantes.PodeConsultar
  @Override
  @GetMapping
  public CollectionModel<RestauranteBasicoModel> listar() {
    return restauranteBasicoModelAssembler
            .toCollectionModel(restauranteRepository.findAll());
  }

  @Restaurantes.PodeConsultar
  @Override
  @GetMapping(params = "projecao=apenas-nome")
  public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
    return restauranteApenasNomeModelAssembler
            .toCollectionModel(restauranteRepository.findAll());
  }


  @Restaurantes.PodeConsultar
  @Override
  @GetMapping("/{restauranteId}")
  public RestauranteModel buscar(@PathVariable Long restauranteId) {

    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return restauranteModelAssembler.toModel(restaurante);
  }

  @Restaurantes.PodeGerenciarCadastro
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

  @Restaurantes.PodeGerenciarCadastro
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

  @Restaurantes.PodeGerenciarCadastro
  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{restauranteId}")
  public void remover(@PathVariable Long restauranteId) {

    cadastroRestaurante.excluir(restauranteId);
  }

  @Restaurantes.PodeGerenciarCadastro
  @Override
  @PutMapping("/{restauranteId}/ativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.ativar(restauranteId);
    return ResponseEntity.noContent().build();
  }

  @Restaurantes.PodeGerenciarCadastro
  @Override
  @DeleteMapping("/{restauranteId}/inativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.inativar(restauranteId);
    return ResponseEntity.noContent().build();
  }


  @Restaurantes.PodeGerenciarCadastro
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

  @Restaurantes.PodeGerenciarCadastro
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


  @Restaurantes.PodeGerenciarFuncionamento
  @Override
  @PutMapping("/{restauranteId}/abertura")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
    cadastroRestaurante.abrir(restauranteId);
    return ResponseEntity.noContent().build();
  }

  @Restaurantes.PodeGerenciarFuncionamento
  @Override
  @PutMapping("/{restauranteId}/fechamento")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
    cadastroRestaurante.fechar(restauranteId);
    return ResponseEntity.noContent().build();
  }

}
