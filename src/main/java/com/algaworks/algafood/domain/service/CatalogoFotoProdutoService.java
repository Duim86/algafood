package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
public class CatalogoFotoProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;
  @Autowired
  private FotoStorageService fotoStorage;

  @Transactional
  public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
    var restauranteId = foto.getProduto().getRestaurante().getId();
    var produtoId = foto.getProduto().getId();
    var nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
    var fotoExistente = produtoRepository.findFotoPorId(restauranteId, produtoId);
    String nomeArquivoExistente = null;

    if(fotoExistente.isPresent()){
      nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
      produtoRepository.delete(fotoExistente.get());
    }

    foto.setNomeArquivo(nomeNovoArquivo);
    foto = produtoRepository.save(foto);
    produtoRepository.flush();

    var novaFoto = FotoStorageService.NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dadosArquivo)
            .build();

    fotoStorage.substituir(nomeArquivoExistente, novaFoto);

    return foto;
  }

  public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
    return produtoRepository.findFotoPorId(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
  }
}
