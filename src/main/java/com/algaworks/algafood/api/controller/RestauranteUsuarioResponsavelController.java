package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private UsuarioModelAssembler usuarioModelAssembler;

  @GetMapping
  public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
  }

  @PutMapping("/{usuarioId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId ) {
    cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
  }

  @DeleteMapping("/{usuarioId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId ) {
    cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
  }
}
