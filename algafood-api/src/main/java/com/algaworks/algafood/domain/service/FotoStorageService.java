package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {
  FotoRecuperada recuperar(String nomeArquivo);

  void armazenar(NovaFoto novaFoto);

  void remover(String nomeArquivo) throws IOException;

  default String gerarNomeArquivo(String nomeOriginal) {
    return UUID.randomUUID() + "_" + nomeOriginal;
  }

  default String getRandomUUID(String nomeArquivo) {
    return nomeArquivo.substring(0, nomeArquivo.indexOf("_"));
  }

  default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) throws IOException {
    this.armazenar(novaFoto);
    if(nomeArquivoAntigo != null) {
      this.remover(nomeArquivoAntigo);
    }
  }

  @Getter
  @Builder
  class NovaFoto {
    private final String nomeArquivo;
    private final InputStream inputStream;
  }

  @Getter
  @Builder
  class FotoRecuperada {
    private final InputStream inputStream;
    private final String url;
    public boolean temUrl() {
      return url != null;
    }

    public boolean temInputStream() {
      return inputStream != null;
    }
  }
}
