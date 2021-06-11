package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = Groups.CozinhaId.class)
  private Long id;

  @NotBlank
  @Column (nullable = false)
  private String nome;

  @JsonIgnore
  @OneToMany(mappedBy = "cozinha")
  private List<Restaurante> restaurantes = new ArrayList<>();
}

