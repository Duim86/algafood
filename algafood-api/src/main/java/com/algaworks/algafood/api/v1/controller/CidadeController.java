package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.core.web.AlgaMediaTypes;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.algaworks.algafood.core.security.CheckSecurity.*;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @Autowired
  private CidadeModelAssembler cidadeModelAssembler;

  @Autowired
  private CidadeInputDisassembler cidadeInputDisassembler;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  @Cidades.PodeConsultar
  public CollectionModel<CidadeModel> listar() {

    return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());

  }

  @Override
  @GetMapping("/{cidadeId}")
  @Cidades.PodeConsultar
  public CidadeModel buscar(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {

    return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Cidades.PodeEditar
  public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput) {
    try {
      var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
      var cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));

      ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

      return cidadeModel;
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @Override
  @PutMapping("/{cidadeId}")
  @ResponseStatus(HttpStatus.OK)
  @Cidades.PodeEditar
  public Cidade atualizar(@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") @RequestBody @Valid CidadeInput cidadeInput,
                          @ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {

    var cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

    cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

    cidadeAtual = cadastroCidade.salvar(cidadeAtual);

    try {
      return cadastroCidade.salvar(cidadeAtual);
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage(), e);
    }
  }

  @Override
  @DeleteMapping("/{cidadeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Cidades.PodeEditar
  public void remover(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {
    cadastroCidade.excluir(cidadeId);
  }
}

