package com.algaworks.algafood.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String senha;

  @CreationTimestamp
  @Column(nullable = false, columnDefinition = "datetime")
  private LocalDateTime dataCadastro;

  @ManyToMany
  @JoinTable(name = "usuario_grupo",
          joinColumns = @JoinColumn(name = "usuario_id"),
          inverseJoinColumns = @JoinColumn(name = "grupo_id"))
  private List<Grupo> grupos = new ArrayList<>();
}