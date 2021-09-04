package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.algaworks.algafood.core.security.CheckSecurity.*;

@RestController
@RequestMapping(path = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {


  @Autowired
  private EstadoRepository estadoRepository;

  @Autowired
  private CadastroEstadoService cadastroEstado;

  @Autowired
  private EstadoModelAssembler estadoModelAssembler;

  @Autowired
  private EstadoInputDisassembler estadoInputDisassembler;

  @Override
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  @Estados.PodeConsultar
  public CollectionModel<EstadoModel> listar() {

    return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
  }

  @Override
  @GetMapping("/{estadoId}")
  @Estados.PodeConsultar
  public EstadoModel buscar(@PathVariable Long estadoId) {

    return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Estados.PodeEditar
  public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
    var estado = estadoInputDisassembler.toDomainObject(estadoInput);
    return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
  }

  @Override
  @PutMapping("/{estadoId}")
  @Estados.PodeEditar
  public EstadoModel atualizar(@RequestBody @Valid EstadoInput estadoInput,
                               @PathVariable Long estadoId) {

    var estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
    estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
    return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{estadoId}")
  @Estados.PodeEditar
  public void remover(@PathVariable Long estadoId) {
    cadastroEstado.excluir(estadoId);
  }
}
