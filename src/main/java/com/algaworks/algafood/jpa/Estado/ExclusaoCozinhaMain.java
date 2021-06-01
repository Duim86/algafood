//package com.algaworks.algafood.jpa.Estado;
//
//import com.algaworks.algafood.AlgafoodApiApplication;
//import com.algaworks.algafood.domain.model.Cozinha;
//import com.algaworks.algafood.domain.repository.CozinhaRepository;
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.ApplicationContext;
//
//public class ExclusaoCozinhaMain {
//  public static void main(String[] args) {
//    ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
//            .web(WebApplicationType.NONE).run(args);
//
//    CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
//
//    Cozinha cozinha = new Cozinha();
//    cozinha.setId(1L);
//
//    cozinhaRepository.remover(cozinha);
//  }
//}
