package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.algaworks.algafood.core.security.CheckSecurity.Restaurantes;

@RestController
@RequestMapping(path ="/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {


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


  @Restaurantes.PodeConsultar
  @Override
  @GetMapping
  public FotoProdutoModel buscar(@PathVariable Long restauranteId,
                                 @PathVariable Long produtoId) {
    var fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);

    return fotoProdutoModelAssembler.toModel(fotoProduto);
  }

  @Override
  @GetMapping(produces = MediaType.ALL_VALUE)
  public ResponseEntity<Object> servirFoto(@PathVariable Long restauranteId,
                                           @PathVariable Long produtoId,
                                           @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
    try {
      var fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
      var fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

      var mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
      var mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
      verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

      if(fotoRecuperada.temUrl()) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                .build();

      } else {
        return ResponseEntity.ok()
                .contentType(mediaTypeFoto)
                .body(new InputStreamResource(fotoRecuperada.getInputStream()));
      }
    } catch (EntidadeNaoEncontradaException e){
      return ResponseEntity.notFound().build();
    }


  }


  @Restaurantes.PodeGerenciarFuncionamento
  @Override
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @Valid FotoProdutoInput fotoProdutoInput,
                                        @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true)
                                        MultipartFile arquivo) throws IOException {


    var produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

    var foto = new FotoProduto();
    foto.setProduto(produto);
    foto.setDescricao(fotoProdutoInput.getDescricao());
    foto.setContentType(arquivo.getContentType());
    foto.setTamanho(arquivo.getSize());
    foto.setNomeArquivo(arquivo.getOriginalFilename());


    var fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());

    return fotoProdutoModelAssembler.toModel(fotoSalva);

  }

  @Restaurantes.PodeGerenciarFuncionamento
  @Override
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long restauranteId,
                      @PathVariable Long produtoId) throws IOException {
    catalogoFotoProduto.excluir(restauranteId, produtoId);
  }

  private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
    var compativel = mediaTypesAceitas.stream()
            .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
    if (!compativel) {
      throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
    }
  }
}
