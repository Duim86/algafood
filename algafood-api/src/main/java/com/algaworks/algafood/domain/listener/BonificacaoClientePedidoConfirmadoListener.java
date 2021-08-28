package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BonificacaoClientePedidoConfirmadoListener {

  @EventListener
  public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
    System.out.println("Calculando pontos para cliente " + event.getPedido().getCliente().getNome());
  }
}
