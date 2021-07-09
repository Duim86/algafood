package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinha2IT {

  @LocalServerPort
  private int port;

  @Autowired
  private CozinhaRepository cozinhaRepository;

  @BeforeEach
  public void setUp() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = port;
    RestAssured.basePath = "/cozinhas";

    prepararDados();
  }

  @Test
  public void deveRetornarStatus200_QuandoConsultarCozinhas() {
         given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200);

  }

  @Test
  public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
    given()
      .accept(ContentType.JSON)
    .when()
      .get()
    .then()
      .body("content.nome", Matchers.hasSize(2))
      .body("content.nome", Matchers.hasItems("Indiana", "Tailandesa"));
  }

  @Test
  public void deveRetornarStatus201_QuandoCadastrarCozinha() {
    given()
            .body("{\"nome\": \"Chinesa\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
    .when()
            .post()
    .then()
            .statusCode(201);
  }

  @Test
  public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
    given()
            .pathParam("cozinhaId", 2)
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(200)
            .body("nome", equalTo("Indiana"));
  }

  @Test
  public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
    given()
            .pathParam("cozinhaId", 100)
            .accept(ContentType.JSON)
            .when()
            .get("/{cozinhaId}")
            .then()
            .statusCode(404);
  }


  private void prepararDados() {
    Cozinha cozinhaTailandesa = new Cozinha();
    cozinhaTailandesa.setNome("Tailandesa");
    cozinhaRepository.save(cozinhaTailandesa);

    Cozinha cozinhaIndiana = new Cozinha();
    cozinhaIndiana.setNome("Indiana");
    cozinhaRepository.save(cozinhaIndiana);
  }
}