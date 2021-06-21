package com.algaworks.algafood.api.dtos.disassembler;

import com.algaworks.algafood.api.model.input.FormaDePagamentoInput;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaDePagamentoInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public FormaDePagamento toDomainObject(FormaDePagamentoInput formaDePagamento) {
    return modelMapper.map(formaDePagamento, FormaDePagamento.class);
  }

  public void copyToDomainObject(FormaDePagamentoInput formaDePagamentoInput, FormaDePagamento formaDePagamento) {

    modelMapper.map(formaDePagamentoInput, formaDePagamento);
  }
}
