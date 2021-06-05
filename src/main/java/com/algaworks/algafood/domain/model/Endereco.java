package com.algaworks.algafood.domain.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Endereco {

  @JoinColumn(name = "endereco_cep")
  private String endereco_cep;

  @JoinColumn(name = "endereco_logradouro")
  private String endereco_logradouro;

  @JoinColumn(name = "endereco_numero")
  private String endereco_numero;

  @JoinColumn(name = "endereco_complemento")
  private String endereco_complemento;

  @JoinColumn(name = "endereco_bairro")
  private String endereco_bairro;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "endereco_cidade_id")
  private Cidade endereco_cidade;

}
