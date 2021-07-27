package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.dtos.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
  @Autowired
  private GrupoRepository grupoRepository;

  @Autowired
  private CadastroGrupoService cadastroGrupo;

  @Autowired
  private GrupoModelAssembler grupoModelAssembler;

  @Autowired
  private GrupoInputDisassembler grupoInputDisassembler;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<GrupoModel> listar() {
    return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
  }

  @Override
  @GetMapping("/{grupoId}")
  public GrupoModel buscar(@PathVariable Long grupoId) {
    return grupoModelAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
      var grupo = grupoInputDisassembler.toDomainObject(grupoInput);
      return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupo));
  }

  @Override
  @PutMapping("/{grupoId}")
  public GrupoModel atualizar(@PathVariable Long grupoId,
                              @RequestBody @Valid GrupoInput grupoInput) {
    var grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

    grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

    grupoAtual = cadastroGrupo.salvar(grupoAtual);

    return grupoModelAssembler.toModel(grupoAtual);
  }

  @Override
  @DeleteMapping("/{grupoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long grupoId) {
    cadastroGrupo.excluir(grupoId);
  }
}

