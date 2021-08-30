package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

  @interface Cozinhas {

    @PreAuthorize("isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface PodeConsultar {

    }

    @PreAuthorize("hasAuthority('EDITAR_COZINHA')")
    @Retention(RUNTIME)
    @Target(METHOD)
    @interface PodeEditar {

    }
  }
}
