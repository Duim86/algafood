package com.algaworks.algafood.infrastructure.email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {


  public SandboxEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties, Configuration freemarkerConfig) {
    super(mailSender, emailProperties, freemarkerConfig);
  }

  @Override
  protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
    var mimeMessage = super.criarMimeMessage(mensagem);

    var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    helper.setTo(emailProperties.getSandbox().getDestinatario());

    return mimeMessage;
  }
}
