package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
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

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {
  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @Autowired
  private CidadeModelAssemblerV2 cidadeModelAssembler;

  @Autowired
  private CidadeInputDisassemblerV2 cidadeInputDisassembler;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public CollectionModel<CidadeModelV2> listar() {

    return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());

  }

  @GetMapping("/{cidadeId}")
  public CidadeModelV2 buscar(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {

    return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CidadeModelV2 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInputV2 cidadeInput) {
    try {
      var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
      var cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));

      ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

      return cidadeModel;
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @PutMapping("/{cidadeId}")
  @ResponseStatus(HttpStatus.OK)
  public Cidade atualizar(@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") @RequestBody @Valid CidadeInputV2 cidadeInput,
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

  @DeleteMapping("/{cidadeId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@ApiParam(value = "Id de uma cidade", example = "1") @PathVariable Long cidadeId) {
    cadastroCidade.excluir(cidadeId);
  }
}

