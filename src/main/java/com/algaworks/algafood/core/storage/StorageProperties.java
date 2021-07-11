package com.algaworks.algafood.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {
  private Local local = new Local();
  private Cloud cloud = new Cloud();
  private TipoStorage tipo = TipoStorage.LOCAL;

  public enum TipoStorage {
    LOCAL, CLOUDINARY
  }

  @Setter
  @Getter
  public static class Cloud {
    private String apiKey;
    private String apiSecret;
    private String cloudName;
    private String diretorioFotos;
  }

  @Setter
  @Getter
  public static class Local {
    private Path diretorioFotos;
  }
}
