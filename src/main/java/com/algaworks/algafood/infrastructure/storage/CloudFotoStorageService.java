package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CloudFotoStorageService implements FotoStorageService {

  private final Cloudinary cloudinary;
  private final StorageProperties storageProperties;

  public CloudFotoStorageService(Cloudinary cloudinary, StorageProperties storageProperties) {
    this.cloudinary = cloudinary;
    this.storageProperties = storageProperties;
  }

  @Override
  public FotoRecuperada recuperar(String nomeArquivo) {
    var url = cloudinary.url().generate(getCaminhoArquivo(nomeArquivo));
    return FotoRecuperada.builder()
            .url(url)
            .build();
  }

  @Override
  public void armazenar(NovaFoto novaFoto) {
    var publicId = getRandomUUID(novaFoto.getNomeArquivo());

    try {
      var foto = convert(novaFoto);
      cloudinary.uploader().upload(foto,
              ObjectUtils.asMap("public_id", publicId,
                      "folder", storageProperties.getCloud().getDiretorioFotos()));
      cleanUp(foto.toPath());
    } catch (Exception e) {
      throw new StorageException("Não foi possível enviar o arquivo para a nuvem");
    }
  }

  @Override
  public void remover(String nomeArquivo) throws IOException {
    try {
      cloudinary.uploader().destroy(getCaminhoArquivo(nomeArquivo), ObjectUtils.emptyMap());
    } catch (Exception e) {
      throw new StorageException("Não foi possível apagar o arquivo na nuvem");
    }
  }

  private File convert(NovaFoto novaFoto) throws IOException {
    var file = new File(novaFoto.getNomeArquivo());
    try (var fo = new FileOutputStream(file)) {
      fo.write(novaFoto.getInputStream().readAllBytes());
    }
    return file;
  }

  public void cleanUp(Path path) throws IOException {
    Files.delete(path);
  }

  public String getCaminhoArquivo(String nomeArquivo){
    return storageProperties.getCloud().getDiretorioFotos()+"/"+getRandomUUID(nomeArquivo);
  }
}
