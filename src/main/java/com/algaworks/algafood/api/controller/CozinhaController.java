package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @GetMapping()
  public List<Cozinha> listar() {
    return cozinhaRepository.findAll();
  }

  @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
  public CozinhasXmlWrapper listarXml() {
    return new CozinhasXmlWrapper(cozinhaRepository.findAll());
  }

  @GetMapping("/{cozinhaId}")
  public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
    Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
//    if(cozinha.isPresent()) {
//      return ResponseEntity.ok((cozinha.get()));
//    } Esse código é substituido pelo abaixo.
    return cozinha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Cozinha adicionar(@RequestBody Cozinha cozinha) {
    return cadastroCozinha.salvar(cozinha);
  }

  @PutMapping("/{cozinhaId}")
  public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha,
                                           @PathVariable Long cozinhaId) {

    Optional<Cozinha> cozinhaAtual = (cozinhaRepository.findById(cozinhaId));

    if (cozinhaAtual.isPresent()) {
      BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");

      Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());

      return ResponseEntity.ok(cozinhaSalva);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

//  @DeleteMapping("/{cozinhaId}")
//  public ResponseEntity<?> remover(@PathVariable Long cozinhaId) {
//    try {
//      cadastroCozinha.excluir(cozinhaId);
//      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//    } catch (EntidadeNaoEncontradaException e) {
//     return ResponseEntity.notFound().build();
//
//    } catch (EntidadeEmUsoException e) {
//      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//    }
//  }

  @DeleteMapping("/{cozinhaId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long cozinhaId) {
    try {
      cadastroCozinha.excluir(cozinhaId);
    } catch (EntidadeNaoEncontradaException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//      throw new ServerWebInputException(e.getMessage());

    }
  }
}
