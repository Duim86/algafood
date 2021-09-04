package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.algaworks.algafood.core.security.CheckSecurity.Restaurantes;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {


  private final ProdutoRepository produtoRepository;
  private final CadastroRestauranteService cadastroRestaurante;
  private final CadastroProdutoService cadastroProduto;
  private final ProdutoModelAssembler produtoModelAssembler;
  private final ProdutoInputDisassembler produtoInputDisassembler;
  private final AlgaLinks algaLinks;

  @Autowired
  public RestauranteProdutoController(ProdutoRepository produtoRepository,
                                      CadastroRestauranteService cadastroRestaurante,
                                      CadastroProdutoService cadastroProduto,
                                      ProdutoModelAssembler produtoModelAssembler,
                                      ProdutoInputDisassembler produtoInputDisassembler, AlgaLinks algaLinks) {
    this.produtoRepository = produtoRepository;
    this.cadastroRestaurante = cadastroRestaurante;
    this.cadastroProduto = cadastroProduto;
    this.produtoModelAssembler = produtoModelAssembler;
    this.produtoInputDisassembler = produtoInputDisassembler;
    this.algaLinks = algaLinks;
  }

  @Restaurantes.PodeConsultar
  @Override
  @GetMapping
  public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                              @RequestParam(required = false) Boolean incluirInativos) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    List<Produto> todosProdutos;

    if (Boolean.TRUE.equals(incluirInativos)) {
      todosProdutos = produtoRepository.findAllByRestaurante(restaurante);
    } else {
      todosProdutos = produtoRepository.findAllByRestauranteAndAtivoTrue(restaurante);
    }
    return produtoModelAssembler.toCollectionModel(todosProdutos)
            .add(algaLinks.linkToProdutos(restauranteId));
  }

  @Restaurantes.PodeConsultar
  @Override
  @GetMapping("/{produtoId}")
  public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

    var produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
    return produtoModelAssembler.toModel(produto);
  }

  @Restaurantes.PodeGerenciarFuncionamento
  @Override
  @PostMapping
  public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
    var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

    var produto = produtoInputDisassembler.toDomainObject(produtoInput);
    produto.setRestaurante(restaurante);

    return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
  }

  @Restaurantes.PodeGerenciarFuncionamento
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
