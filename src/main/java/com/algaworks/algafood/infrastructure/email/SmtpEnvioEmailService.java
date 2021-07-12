package com.algaworks.algafood.infrastructure.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
public class SmtpEnvioEmailService implements EnvioEmailService {

  public final JavaMailSender mailSender;
  public final EmailProperties emailProperties;
  public final freemarker.template.Configuration freemarkerConfig;


  @Override
  public void enviar(Mensagem mensagem) {
    try {
      var mimeMessage = criarMimeMessage(mensagem);

      mailSender.send(mimeMessage);
    } catch (Exception e) {
      throw new EmailException("Não foi possível enviar e-mail", e);
    }
  }

  protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
    String corpo = processarTemplate(mensagem);

    var mimeMessage = mailSender.createMimeMessage();

    var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    helper.setFrom(emailProperties.getRemetente());
    helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
    helper.setSubject(mensagem.getAssunto());
    helper.setText(corpo, true);

    return mimeMessage;
  }

  protected String processarTemplate (Mensagem mensagem) {
    try {
      var template = freemarkerConfig.getTemplate(mensagem.getCorpo());
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
    } catch (Exception e) {
      throw new EmailException("Não foi possível montar o template do email", e);
    }
  }

}
