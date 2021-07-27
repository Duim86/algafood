package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.dtos.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {


  private final ProdutoRepository produtoRepository;
  private final CadastroRestauranteService cadastroRestaurante;
  private final CadastroProdutoService cadastroProduto;
  private final ProdutoModelAssembler produtoModelAssembler;
  private final ProdutoInputDisassembler produtoInputDisassembler;

  @Autowired
  public RestauranteProdutoController(ProdutoRepository produtoRepository,
                                      CadastroRestauranteService cadastroRestaurante,
                                      CadastroProdutoService cadastroProduto,
                                      ProdutoModelAssembler produtoModelAssembler,
                                      ProdutoInputDisassembler produtoInputDisassembler) {
    this.produtoRepository = produtoRepository;
    this.cadastroRestaurante = cadastroRestaurante;
    this.cadastroProduto = cadastroProduto;
    this.produtoModelAssembler = produtoModelAssembler;
    this.produtoInputDisassembler = produtoInputDisassembler;
  }

  @Override
  @GetMapping
  public List<ProdutoModel> listar(@PathVariable Long restauranteId,
                                   @RequestParam(required = false) boolean incluirInativos) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    if(incluirInativos) {
     return produtoModelAssembler.toCollectionModel(produtoRepository.findAllByRestaurante(restaurante));
    } else {
     return  produtoModelAssembler.toCollectionModel(produtoRepository.findAllByRestauranteAndAtivoTrue(restaurante));
    }
  }

  @Override
  @GetMapping("/{produtoId}")
  public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

    var produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
    return produtoModelAssembler.toModel(produto);
  }

  @Override
  @PostMapping
  public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    var produto = produtoInputDisassembler.toDomainObject(produtoInput);
    produto.setRestaurante(restaurante);

    return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
  }

  @Override
  @PutMapping("/{produtoId}")
  public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoInput produtoInput) {
    var produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

    produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

    produtoAtual = cadastroProduto.salvar(produtoAtual);

    return produtoModelAssembler.toModel(produtoAtual);
  }
}
