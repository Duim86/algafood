package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.email.SmtpEnvioEmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@AllArgsConstructor
public class EmailConfig {

  private final JavaMailSender mailSender;
  private final EmailProperties emailProperties;
  private final freemarker.template.Configuration freemarkerConfig;

  @Bean
  public EnvioEmailService envioEmailService() {
    switch (emailProperties.getImpl()) {
      case FAKE:
        return new FakeEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
      case SMTP:
        return new SmtpEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
      case SANDBOX:
        return new SandboxEnvioEmailService(mailSender, emailProperties, freemarkerConfig);
      default:
        return null;
    }
  }
}
