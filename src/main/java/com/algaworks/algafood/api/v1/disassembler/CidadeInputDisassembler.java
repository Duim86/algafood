package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Cidade toDomainObject(CidadeInput cidade) {
    return modelMapper.map(cidade, Cidade.class);
  }

  public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {

    modelMapper.map(cidadeInput, cidade);
  }
}
