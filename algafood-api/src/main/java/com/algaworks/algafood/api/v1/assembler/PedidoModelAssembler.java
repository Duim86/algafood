package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.*;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class PedidoModelAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AlgaLinks algaLinks;

  @Autowired
  private AlgaSecurity algaSecurity;

  public PedidoModelAssembler() {
    super(PedidoController.class, PedidoModel.class);
  }

  @Override
  public PedidoModel toModel(Pedido pedido) {
    PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
    modelMapper.map(pedido, pedidoModel);

    pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

    if(algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
      if (pedido.podeSerConfirmado()) {
        pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
      }
      if (pedido.podeSerCancelado()) {
        pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
      }
      if (pedido.podeSerEntregue()) {
        pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
      }
    }

      pedidoModel.getRestaurante().add(
              algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

      pedidoModel.getCliente().add(
              algaLinks.linkToUsuario(pedido.getCliente().getId()));

      pedidoModel.getFormaPagamento().add(
              algaLinks.linkToFormaPagamento(pedido.getFormaDePagamento().getId()));

      pedidoModel.getEnderecoEntrega().getCidade().add(
              algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

      pedidoModel.getItens().forEach(item -> item.add(algaLinks.linkToProduto(
              pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto")));

      return pedidoModel;
    }
}
