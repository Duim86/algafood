package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

  @Autowired
  private EstadoRepository estadoRepository;

  @Autowired
  private CadastroEstadoService cadastroEstado;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<Estado> listar() {
    return estadoRepository.listar();
  }

  @GetMapping("/{estadoId}")
  public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
    Estado estado = estadoRepository.buscar(estadoId);

    if(estado == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(estado);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Estado adicionar(@RequestBody Estado estado) {
    return cadastroEstado.salvar(estado);
  }

  @PutMapping("/{estadoId}")
  public ResponseEntity<?> atualizar(@RequestBody Estado estado,
                                     @PathVariable Long estadoId) {

    Estado estadoAtual = (estadoRepository.buscar(estadoId));

    if (estadoAtual != null) {
      BeanUtils.copyProperties(estado, estadoAtual, "id");

      cadastroEstado.salvar(estadoAtual);

      return ResponseEntity.ok(estadoAtual);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{estadoId}")
  public ResponseEntity<?> remover(@PathVariable Long estadoId) {
    try {
      cadastroEstado.excluir(estadoId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(e.getMessage());
    }
  }
}