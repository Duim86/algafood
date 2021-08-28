package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.FormaDePagamentoController;
import com.algaworks.algafood.api.v1.model.FormaDePagamentoModel;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class FormaDePagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaDePagamento, FormaDePagamentoModel> {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AlgaLinks algaLinks;

  public FormaDePagamentoModelAssembler() {
    super(FormaDePagamentoController.class, FormaDePagamentoModel.class);
  }

  @Override
  public FormaDePagamentoModel toModel(FormaDePagamento formaDePagamento) {
    FormaDePagamentoModel formaDePagamentoModel =
            createModelWithId(formaDePagamento.getId(), formaDePagamento);

    modelMapper.map(formaDePagamento, formaDePagamentoModel);

    formaDePagamentoModel.add(algaLinks.linkToFormasDePagamento("formasPagamento"));

    return formaDePagamentoModel;
  }

  @Override
  public CollectionModel<FormaDePagamentoModel> toCollectionModel(Iterable<? extends FormaDePagamento> entities) {
    return super.toCollectionModel(entities)
            .add(algaLinks.linkToFormasDePagamento());
  }
}
