package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIT {

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @Test
  public void testarCadastroCozinhaComSucesso() { //HappyPath
    //cenário
    Cozinha novaCozinha = new Cozinha();
    novaCozinha.setNome("Chinesa");
    //ação
    novaCozinha = cadastroCozinha.salvar(novaCozinha);

    //validação
    assertThat(novaCozinha).isNotNull();
    assertThat(novaCozinha.getId()).isNotNull();
  }

  @Test
  public void testarCozinhaSemNome() {
    Cozinha novaCozinha = new Cozinha();
    novaCozinha.setNome(null);

    DataIntegrityViolationException erroEsperado =
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
              cadastroCozinha.salvar(novaCozinha);
            });

    assertThat(erroEsperado).isNotNull();
  }

  @Test
  public void deveFalhar_QuandoExcluirCozinhaEmUso() {
    EntidadeEmUsoException erroEsperado =
            Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
              cadastroCozinha.excluir(1L);
            });

    assertThat(erroEsperado).isNotNull();
  }

  @Test
  public void deveFalhar_QuandoExcluirCozinhaInexistente() {
    CozinhaNaoEncontradaException erroEsperado =
            Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
              cadastroCozinha.excluir(100L);
            });
    assertThat(erroEsperado).isNotNull();
  }
}
