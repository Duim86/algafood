package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
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
  public List<EstadoModel> listar() {

    return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
  }

  @Override
  @GetMapping("/{estadoId}")
  public EstadoModel buscar(@PathVariable Long estadoId) {

    return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
    var estado = estadoInputDisassembler.toDomainObject(estadoInput);
    return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
  }

  @Override
  @PutMapping("/{estadoId}")
  public EstadoModel atualizar(@RequestBody @Valid EstadoInput estadoInput,
                               @PathVariable Long estadoId) {

    var estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
    estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
    return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
  }

  @Override
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{estadoId}")
  public void remover(@PathVariable Long estadoId) {
    cadastroEstado.excluir(estadoId);
  }
}
