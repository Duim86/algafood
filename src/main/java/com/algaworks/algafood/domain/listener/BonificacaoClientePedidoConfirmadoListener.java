package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BonificacaoClientePedidoConfirmadoListener {

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
    System.out.println("Calculando pontos para cliente " + event.getPedido().getCliente().getNome());
  }
}
