package com.algaworks.algafood.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

  private String endereco_cep;
  private String endereco_logradouro;
  private String endereco_numero;
  private String endereco_complemento;
  private String endereco_bairro;
//  private CidadeModel endereco_cidade;
}
