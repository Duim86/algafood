package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CadastroPermissaoService {
  @Autowired
  private PermissaoRepository permissaoRepository;

  @Transactional
  public Permissao salvar(Permissao permissao) {
    return permissaoRepository.save(permissao);
  }

  public Permissao buscarOuFalhar(Long permissaoId) {
    return permissaoRepository.findById(permissaoId).
            orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
  }
}
