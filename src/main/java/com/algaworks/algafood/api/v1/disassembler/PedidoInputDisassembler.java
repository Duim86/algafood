package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Pedido toDomainObject(PedidoInput pedido) {
    return modelMapper.map(pedido, Pedido.class);
  }

  public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {

    modelMapper.map(pedidoInput, pedido);
  }
}
