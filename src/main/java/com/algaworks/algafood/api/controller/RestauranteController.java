package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private SmartValidator validator;

  @Autowired
  private RestauranteModelAssembler restauranteModelAssembler;

  @Autowired
  private RestauranteInputDisassembler restauranteInputDisassembler;

  @GetMapping
  public List<RestauranteModel> listar() {

    return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
  }

  @GetMapping("/{restauranteId}")
  public RestauranteModel buscar(@PathVariable Long restauranteId) {

    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return restauranteModelAssembler.toModel(restaurante);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestauranteModel adicionar(@RequestBody
                                    @Valid RestauranteInput restauranteInput) {
    try {
      Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
      return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @PutMapping("/{restauranteId}")
  public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInput restauranteInput) {
//    Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
    Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

    restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

//    BeanUtils.copyProperties(restaurante, restauranteAtual,
//            "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
    try {
      return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
    } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{restauranteId}")
  public void remover(@PathVariable Long restauranteId) {

    cadastroRestaurante.excluir(restauranteId);
  }

  @PutMapping("/{restauranteId}/ativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.ativar(restauranteId);
  }

  @DeleteMapping("/{restauranteId}/inativo")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void inativar(@PathVariable Long restauranteId) {
    cadastroRestaurante.inativar(restauranteId);
  }


  @PutMapping("/ativacoes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void ativarEmMassa(@RequestBody List<Long> restauranteIds) {
    try {
      cadastroRestaurante.ativar(restauranteIds);
    } catch (RestauranteNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @DeleteMapping("/ativacoes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void inativarEmMassa(@RequestBody List<Long> restauranteIds) {
    try {
      cadastroRestaurante.inativar(restauranteIds);
    } catch (RestauranteNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }


  @PutMapping("/{restauranteId}/abertura")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void abrir(@PathVariable Long restauranteId) {
    cadastroRestaurante.abrir(restauranteId);
  }

  @PutMapping("/{restauranteId}/fechamento")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void fechar(@PathVariable Long restauranteId) {
    cadastroRestaurante.fechar(restauranteId);
  }

//  @PatchMapping("/{restauranteId}")
//  public RestauranteModel atualizarParcial(@PathVariable Long restauranteId,
//                                      @RequestBody Map<String, Object> campos,
//                                      HttpServletRequest request) {
//    Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//    merge(campos, restauranteAtual, request);
//
//    validate(restauranteAtual, "restaurante");
//
//    return atualizar(restauranteId, restauranteAtual);
//  }

//  private void validate(Restaurante restaurante, String objectName) {
//
//    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//
//    validator.validate(restaurante, bindingResult);
//
//    if(bindingResult.hasErrors()) {
//      throw new ValidacaoException(bindingResult);
//    }
//
//  }
//
//  private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
//    ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//    try {
//      ObjectMapper objectMapper = new ObjectMapper();
//      objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//      Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//
//      dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//        Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//
//        if (field != null) {
//          field.setAccessible(true);
//          Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//          ReflectionUtils.setField(field, restauranteDestino, novoValor);
//        }
//      });
//    } catch (IllegalArgumentException e) {
//      Throwable rootCause = ExceptionUtils.getRootCause(e);
//      throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//    }
//  }
}
