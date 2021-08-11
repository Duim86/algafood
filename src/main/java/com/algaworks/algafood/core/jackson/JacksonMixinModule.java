package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.v1.model.mixin.CidadeMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

  private static final long serialVersionUID = 1L;

  public JacksonMixinModule() {
    setMixInAnnotation(Cidade.class, CidadeMixin.class);
  }
}
