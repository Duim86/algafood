package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class HateoasConfiguration {

  @Bean
  public HalConfiguration halConfiguration() {
    return new HalConfiguration()
            .withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON)
            .withMediaType(AlgaMediaTypes.V2_APPLICATION_JSON)
            .withMediaType(MediaType.APPLICATION_JSON);
  }
}