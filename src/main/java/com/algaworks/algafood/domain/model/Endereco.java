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
  private String cep;

  @JoinColumn(name = "endereco_logradouro")
  private String logradouro;

  @JoinColumn(name = "endereco_numero")
  private String numero;

  @JoinColumn(name = "endereco_complemento")
  private String complemento;

  @JoinColumn(name = "endereco_bairro")
  private String bairro;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "endereco_cidade_id")
  private Cidade cidade;

}
