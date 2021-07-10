package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dtos.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {


  private final CadastroProdutoService cadastroProdutoService;
  private final CatalogoFotoProdutoService catalogoFotoProduto;
  private final FotoProdutoModelAssembler fotoProdutoModelAssembler;
  private final FotoStorageService fotoStorageService;

  @Autowired
  public RestauranteProdutoFotoController(CadastroProdutoService cadastroProdutoService,
                                          CatalogoFotoProdutoService catalogoFotoProduto,
                                          FotoProdutoModelAssembler fotoProdutoModelAssembler,
                                          FotoStorageService fotoStorageService) {
    this.cadastroProdutoService = cadastroProdutoService;
    this.catalogoFotoProduto = catalogoFotoProduto;
    this.fotoProdutoModelAssembler = fotoProdutoModelAssembler;
    this.fotoStorageService = fotoStorageService;
  }


  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public FotoProdutoModel buscar(@PathVariable Long restauranteId,
                                 @PathVariable Long produtoId) {
    var fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

    return fotoProdutoModelAssembler.toModel(fotoProduto);
  }

  @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId) {
    try {
      var fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

      var inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

      return ResponseEntity.ok()
              .contentType(MediaType.IMAGE_JPEG)
              .body(new InputStreamResource(inputStream));
    } catch (EntidadeNaoEncontradaException e) {
      return ResponseEntity.notFound().build();
    }


  }

  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @Valid FotoProdutoInput fotoProdutoInput) throws IOException {


    var produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

    var arquivo = fotoProdutoInput.getArquivo();

    var foto = new FotoProduto();
    foto.setProduto(produto);
    foto.setDescricao(fotoProdutoInput.getDescricao());
    foto.setContentType(arquivo.getContentType());
    foto.setTamanho(arquivo.getSize());
    foto.setNomeArquivo(arquivo.getOriginalFilename());


    var fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

    return fotoProdutoModelAssembler.toModel(fotoSalva);

  }

}