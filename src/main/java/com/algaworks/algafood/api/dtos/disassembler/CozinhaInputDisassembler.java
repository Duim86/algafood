package com.algaworks.algafood.api.dtos.disassembler;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Cozinha toDomainObject(CozinhaInput cozinha) {
    return modelMapper.map(cozinha, Cozinha.class);
  }

  public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {

    modelMapper.map(cozinhaInput, cozinha);
  }
}
