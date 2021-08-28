package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoCanceladoListener {

  private final EnvioEmailService envioEmail;

  public NotificacaoClientePedidoCanceladoListener(EnvioEmailService envioEmailService) {
    this.envioEmail = envioEmailService;
  }

  @EventListener
  public void aoCancelarPedido(PedidoCanceladoEvent event) {
    var pedido = event.getPedido();

    var mensagem = EnvioEmailService.Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado!")
            .corpo("pedido-cancelado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();
    envioEmail.enviar(mensagem);
  }
}
