package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCidadeServiceTest {
  @Autowired
  CadastroCidadeService cadastroCidade;

  @Test
  void testarCadastroCidadeComSucesso() {
    //cenário
    var novoEstado = new Estado();
    novoEstado.setId(1L);
    var novaCidade = new Cidade();

    novaCidade.setNome("Matinhos");
    novaCidade.setEstado(novoEstado);

    //acao
    novaCidade = cadastroCidade.salvar((novaCidade));

    //validacao

    assertThat(novaCidade).isNotNull();
    assertThat(novaCidade.getId()).isNotNull();
  }

  @Test
  void deveFalhar_QuandoExcluirCidadeEmUso() {
    EntidadeEmUsoException erroEsperado =
            Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
              cadastroCidade.excluir(1L);
            });

    assertThat(erroEsperado).isNotNull();
  }

  @Test
  void deveFalhar_QuandoExcluirCidadeInexistente() {
    CidadeNaoEncontradaException erroEsperado =
            Assertions.assertThrows(CidadeNaoEncontradaException.class, () -> {
              cadastroCidade.excluir(100L);
            });
    assertThat(erroEsperado).isNotNull();
  }

  @Test
  void testarBuscarCidadePorIdComSucesso() {
    var novaCidade = cadastroCidade.buscarOuFalhar(1L);

    assertThat(novaCidade).isNotNull();
    assertThat(novaCidade.getId()).isNotNull();

  }

  @Test
  void deveFalhar_QuandoBuscarCidadeInexistente() {
    CidadeNaoEncontradaException erroEsperado =
            Assertions.assertThrows(CidadeNaoEncontradaException.class, () -> {
              cadastroCidade.buscarOuFalhar(100L);
            });
    assertThat(erroEsperado).isNotNull();
  }
}