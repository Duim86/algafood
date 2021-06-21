package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutoRestauranteController {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private ProdutoModelAssembler produtoModelAssembler;

  @GetMapping
  public List<ProdutoModel> listar(@PathVariable Long restauranteId){

    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return produtoModelAssembler.toCollectionModel(produtoRepository.findAllByRestaurante(restaurante));

  }

  @GetMapping("/{produtoId}")
  public ProdutoModel buscar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {

    Produto produto = produtoRepository
            .findByIdAndRestauranteId(produtoId, restauranteId)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    return produtoModelAssembler.toModel(produto);
  }
}
