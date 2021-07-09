package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomJpaRepository<T, I> extends JpaRepository<T, I> {
  Optional<T> buscarPrimeiro();

  void detach(T entity);
}
