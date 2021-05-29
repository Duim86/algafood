package com.algaworks.algafood.jpa.Restaurante;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE).run(args);

    RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

    Restaurante restaurante1 = new Restaurante();
    restaurante1.setNome("Restaurante do Duim");
    restaurante1.setTaxaFrete(new BigDecimal("7.25"));
    Restaurante restaurante2 = new Restaurante();
    restaurante2.setNome("Famiglia Zanlorenzi");
    restaurante2.setTaxaFrete(new BigDecimal("12.5"));

    restauranteRepository.salvar(restaurante1);
    restauranteRepository.salvar(restaurante2);
  }
}
