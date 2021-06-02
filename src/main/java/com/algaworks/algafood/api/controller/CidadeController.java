package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
  @Autowired
  private CidadeRepository cidadeRepository;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<Cidade> listar() {
    return cidadeRepository.findAll();
  }

  @GetMapping("/{cidadeId}")
  public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
    Cidade cidade = cidadeRepository.findById(cidadeId).orElse(null);

    if (cidade != null) {
      return ResponseEntity.ok(cidade);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCidade.salvar(cidade));
    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{cidadeId}")
  public ResponseEntity<?> atualizar(@RequestBody Cidade cidade,
                                     @PathVariable Long cidadeId) {

    try {
      Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);

      if (cidadeAtual != null) {
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeAtual = cadastroCidade.salvar(cidadeAtual);
        return ResponseEntity.ok(cidadeAtual);
      }

      return ResponseEntity.notFound().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.badRequest()
              .body(e.getMessage());
    }
  }

  @DeleteMapping("/{cidadeId}")
  public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId) {
    try {
      cadastroCidade.excluir(cidadeId);
      return ResponseEntity.noContent().build();

    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();

    } catch (EntidadeEmUsoException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }
}

