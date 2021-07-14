package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

  private final EnvioEmailService envioEmail;

  public NotificacaoClientePedidoConfirmadoListener(EnvioEmailService envioEmailService) {
    this.envioEmail = envioEmailService;
  }

  @EventListener
  public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
    var pedido = event.getPedido();

    var mensagem = EnvioEmailService.Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();
    envioEmail.enviar(mensagem);
  }
}
