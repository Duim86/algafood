package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.CozinhaModel;
import com.algaworks.algafood.api.dtos.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @Autowired
  private CozinhaModelAssembler cozinhaModelAssembler;

  @Autowired
  private CozinhaInputDisassembler cozinhaInputDisassembler;

  @GetMapping()
  public List<CozinhaModel> listar() {
    return cozinhaModelAssembler.toCollectionModel(cozinhaRepository.findAll());
  }

  @GetMapping("/{cozinhaId}")
  public CozinhaModel buscar(@PathVariable Long cozinhaId) {
    return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

    Cozinha restaurante = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(restaurante));
  }

  @PutMapping("/{cozinhaId}")
  public CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput,
                           @PathVariable Long cozinhaId) {

    Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

    // BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

  }

  @DeleteMapping("/{cozinhaId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long cozinhaId) {
    cadastroCozinha.excluir(cozinhaId);
  }
}
