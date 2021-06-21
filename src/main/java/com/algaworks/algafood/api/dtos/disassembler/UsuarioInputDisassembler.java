package com.algaworks.algafood.api.dtos.disassembler;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Usuario toDomainObject(UsuarioInput usuario) {
    return modelMapper.map(usuario, Usuario.class);
  }

  public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {

    modelMapper.map(usuarioInput, usuario);
  }
}
