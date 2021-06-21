package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

  @Autowired
  private ProdutoRepository produtoRepository;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private CadastroProdutoService cadastroProduto;

  @Autowired
  private ProdutoModelAssembler produtoModelAssembler;

  @Autowired
  private ProdutoInputDisassembler produtoInputDisassembler;

  @GetMapping
  public List<ProdutoModel> listar(@PathVariable Long restauranteId) {

    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    return produtoModelAssembler.toCollectionModel(produtoRepository.findAllByRestaurante(restaurante));

  }

  @GetMapping("/{produtoId}")
  public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

    Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
    return produtoModelAssembler.toModel(produto);
  }

  @PostMapping
  public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
    produto.setRestaurante(restaurante);

    return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
  }

  @PutMapping("/{produtoId}")
  public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoInput produtoInput) {
    Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

    produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

    produtoAtual = cadastroProduto.salvar(produtoAtual);

    return produtoModelAssembler.toModel(produtoAtual);
  }
}
