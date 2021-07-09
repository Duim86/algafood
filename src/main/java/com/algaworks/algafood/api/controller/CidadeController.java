package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @Autowired
  private CidadeModelAssembler cidadeModelAssembler;

  @Autowired
  private CidadeInputDisassembler cidadeInputDisassembler;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<CidadeModel> listar() {
    return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
  }

  @GetMapping("/{cidadeId}")
  public CidadeModel buscar(@PathVariable Long cidadeId) {
    return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
    try {
      Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
      return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
    } catch (EstadoNaoEncontradoException e) {
      throw new NegocioException(e.getMessage());
    }
  }

  @PutMapping("/{cidadeId}")
  @ResponseStatus(HttpStatus.OK)
  public Cidade atualizar(@RequestBody @Valid CidadeInput cidadeInput,
                          @PathVariable Long cidadeId) {

    Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

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
  public void remover(@PathVariable Long cidadeId) {
    cadastroCidade.excluir(cidadeId);
  }
}

