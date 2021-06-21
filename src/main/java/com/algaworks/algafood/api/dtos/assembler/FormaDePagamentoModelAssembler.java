package com.algaworks.algafood.api.dtos.assembler;

import com.algaworks.algafood.api.model.FormaDePagamentoModel;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class FormaDePagamentoModelAssembler {

  @Autowired
  private ModelMapper modelMapper;



  public FormaDePagamentoModel toModel(FormaDePagamento formaDePagamento) {
    return modelMapper.map(formaDePagamento, FormaDePagamentoModel.class);
  }

  public List<FormaDePagamentoModel> toCollectionModel(Collection<FormaDePagamento> formasDePagamento){
    return formasDePagamento.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
  }
}
