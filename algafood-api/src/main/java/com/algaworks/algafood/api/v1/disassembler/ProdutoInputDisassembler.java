package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Produto toDomainObject(ProdutoInput produto) {
    return modelMapper.map(produto, Produto.class);
  }

  public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {

    modelMapper.map(produtoInput, produto);
  }
}
