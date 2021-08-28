package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

  private final EmissaoPedidoService emissaoPedido;
  private final PedidoRepository pedidoRepository;

  @Autowired
  public FluxoPedidoService(EmissaoPedidoService emissaoPedido, PedidoRepository pedidoRepository) {
    this.emissaoPedido = emissaoPedido;
    this.pedidoRepository = pedidoRepository;
  }


  @Transactional
  public void confirmar(String codigoPedido) {
    var pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
    pedido.confirmar();
    pedidoRepository.save(pedido);
  }

  @Transactional
  public void entregar(String codigoPedido) {
    var pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
    pedido.entregar();
  }

  @Transactional
  public void cancelar(String codigoPedido) {
    var pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
    pedido.cancelar();
    pedidoRepository.save(pedido);
  }
}
