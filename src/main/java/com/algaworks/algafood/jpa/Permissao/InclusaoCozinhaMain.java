package com.algaworks.algafood.jpa.Permissao;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE).run(args);

    CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

    Cozinha cozinha1 = new Cozinha();
    cozinha1.setNome("Brasileira");
    Cozinha cozinha2 = new Cozinha();
    cozinha2.setNome("Japonesa");

    cozinhaRepository.salvar(cozinha1);
    cozinhaRepository.salvar(cozinha2);
  }
}