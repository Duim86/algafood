package com.algaworks.algafood.api.dtos.disassembler;

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Estado toDomainObject(EstadoInput estado) {
    return modelMapper.map(estado, Estado.class);
  }

  public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {

    modelMapper.map(estadoInput, estado);
  }
}
