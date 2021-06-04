package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.model.Permissao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToMany
  @JoinTable(name = "grupo_permissao",
          joinColumns = @JoinColumn(name = "grupo_id"),
          inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private List<Permissao> permissoes = new ArrayList<>();

}
