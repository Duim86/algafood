package com.algaworks.algafood.jpa.Cidade;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoEstadoMain {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE).run(args);

    EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

    Estado estado1 = new Estado();
    estado1.setNome("Paraná");
    Estado estado2 = new Estado();
    estado2.setNome("Bahia");

    estadoRepository.salvar(estado1);
    estadoRepository.salvar(estado2);
  }
}
