package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.FormaDePagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import com.algaworks.algafood.domain.repository.FormaDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroFormaDePagamentoService {

  private static final String MSG_FORMA_DE_PAGAMENTO_EM_USO  =
          "Forma de Pagamento de código %d não pode ser removido, pois está em uso";

  @Autowired
  private FormaDePagamentoRepository formaDePagamentoRepository;

  @Transactional
  public FormaDePagamento salvar(FormaDePagamento formaDePagamento) {

    return formaDePagamentoRepository.save(formaDePagamento);
  }

  @Transactional
  public void excluir(Long formaDePagamentoId) {
    try {
      formaDePagamentoRepository.deleteById(formaDePagamentoId);
      formaDePagamentoRepository.flush();
    } catch (EmptyResultDataAccessException e) {
      throw new EstadoNaoEncontradoException(formaDePagamentoId);
    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
              String.format(MSG_FORMA_DE_PAGAMENTO_EM_USO, formaDePagamentoId));
    }
  }


  public FormaDePagamento buscarOuFalhar(Long formaDePagamentoId) {
    return formaDePagamentoRepository.findById(formaDePagamentoId)
            .orElseThrow(() -> new FormaDePagamentoNaoEncontradoException(formaDePagamentoId));
  }
}
