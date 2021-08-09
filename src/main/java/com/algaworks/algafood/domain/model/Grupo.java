package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
  @JoinTable(
          name = "grupo_permissao",
          joinColumns = @JoinColumn(name = "grupo_id"),
          inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private Set<Permissao> permissoes = new HashSet<>();

  public void adicionarPermissao(Permissao permissao) {
    getPermissoes().add(permissao);
  }

  public void removerPermissao(Permissao permissao) {
    getPermissoes().remove(permissao);
  }

}
