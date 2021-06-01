package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @GetMapping()
  public List<Cozinha> listar() {
    return cozinhaRepository.listar();
  }

  @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
  public CozinhasXmlWrapper listarXml() {
    return new CozinhasXmlWrapper(cozinhaRepository.listar());
  }

  @GetMapping("/{cozinhaId}")
  public Cozinha buscar(@PathVariable Long cozinhaId) {
    return cozinhaRepository.buscar(cozinhaId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Cozinha adicionar(@RequestBody Cozinha cozinha) {
    return cozinhaRepository.salvar(cozinha);
  }

  @PutMapping("/{cozinhaId}")
  public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha,
                                           @PathVariable Long cozinhaId) {

    Cozinha cozinhaAtual = (cozinhaRepository.buscar(cozinhaId));

    if (cozinhaAtual != null) {
      BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

      cozinhaRepository.salvar(cozinhaAtual);

      return ResponseEntity.ok(cozinhaAtual);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
