package com.algaworks.algafood.core.email;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
  private Sandbox sandbox = new Sandbox();
  private Implementacao impl = Implementacao.FAKE;

  @NotNull
  private String remetente;

  public enum Implementacao {
    SMTP, FAKE, SANDBOX
  }

  @Getter
  @Setter
  public class Sandbox {

    private String destinatario;

  }
}
