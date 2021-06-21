package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {
  @Autowired
  private GrupoRepository grupoRepository;

  @Autowired
  private CadastroGrupoService cadastroGrupo;

  @Autowired
  private GrupoModelAssembler grupoModelAssembler;

  @Autowired
  private GrupoInputDisassembler grupoInputDisassembler;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<Grupo> listar() {
    return grupoRepository.findAll();
  }

  @GetMapping("/{grupoId}")
  public Grupo buscar(@PathVariable Long grupoId) {
    return cadastroGrupo.buscarOuFalhar(grupoId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
      Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
      return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupo));
  }

  @PutMapping("/{grupoId}")
  public GrupoModel atualizar(@PathVariable Long grupoId,
                              @RequestBody @Valid GrupoInput grupoInput) {
    Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

    grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

    grupoAtual = cadastroGrupo.salvar(grupoAtual);

    return grupoModelAssembler.toModel(grupoAtual);
  }

  @DeleteMapping("/{grupoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long grupoId) {
    cadastroGrupo.excluir(grupoId);
  }
}

