package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @Autowired
  private CozinhaModelAssemblerV2 cozinhaModelAssembler;

  @Autowired
  private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

  @GetMapping()
  public PagedModel<CozinhaModelV2> listar(Pageable pageable) {
    var cozinhasPage = cozinhaRepository.findAll(pageable);

    return pagedResourcesAssembler
            .toModel(cozinhasPage, cozinhaModelAssembler);
  }

  @GetMapping("/{cozinhaId}")
  public CozinhaModelV2 buscar(@PathVariable Long cozinhaId) {
    return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CozinhaModelV2 adicionar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {

    var restaurante = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(restaurante));
  }

  @PutMapping("/{cozinhaId}")
  public CozinhaModelV2 atualizar(@RequestBody @Valid CozinhaInputV2 cozinhaInput,
                                @PathVariable Long cozinhaId) {

    var cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

  }

  @DeleteMapping("/{cozinhaId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long cozinhaId) {
    cadastroCozinha.excluir(cozinhaId);
  }
}
