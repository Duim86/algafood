package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @Autowired
  private CozinhaModelAssembler cozinhaModelAssembler;

  @Autowired
  private CozinhaInputDisassembler cozinhaInputDisassembler;

  @Override
  @GetMapping()
  public Page<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
    Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

    List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());

    return new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
  }

  @Override
  @GetMapping("/{cozinhaId}")
  public CozinhaModel buscar(@PathVariable Long cozinhaId) {
    return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

    var restaurante = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(restaurante));
  }

  @Override
  @PutMapping("/{cozinhaId}")
  public CozinhaModel atualizar(@RequestBody @Valid CozinhaInput cozinhaInput,
                                @PathVariable Long cozinhaId) {

    var cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

    // BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
    return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));

  }

  @Override
  @DeleteMapping("/{cozinhaId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long cozinhaId) {
    cadastroCozinha.excluir(cozinhaId);
  }
}
