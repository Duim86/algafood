package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {
  private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

  @Autowired
  private RestauranteRepository restauranteRepository;

  @Autowired
  private CadastroCozinhaService cadastroCozinha;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @Autowired
  private CadastroFormaDePagamentoService cadastroFormaDePagamento;

  @Autowired
  private CadastroUsuarioService cadastroUsuario;

  @Transactional
  public Restaurante salvar(Restaurante restaurante) {
    Long cozinhaId = restaurante.getCozinha().getId();
    Long cidadeId = restaurante.getEndereco().getCidade().getId();

    var cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
    var cidade = cadastroCidade.buscarOuFalhar(cidadeId);

    restaurante.setCozinha(cozinha);
    restaurante.getEndereco().setCidade(cidade);

    return restauranteRepository.save(restaurante);
  }

  @Transactional
  public void excluir(Long restauranteId) {
    try {
      restauranteRepository.deleteById(restauranteId);
      restauranteRepository.flush();
    } catch (EmptyResultDataAccessException e) {
      throw new RestauranteNaoEncontradoException(restauranteId);

    } catch (DataIntegrityViolationException e) {
      throw new EntidadeEmUsoException(
              String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
    }
  }

  @Transactional
  public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
    var restaurante = buscarOuFalhar(restauranteId);
    var formaDePagamento = cadastroFormaDePagamento.buscarOuFalhar(formaPagamentoId);

    restaurante.adicionarFormaPagamento(formaDePagamento);
  }


  @Transactional
  public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
    var restaurante = buscarOuFalhar(restauranteId);
    var formaDePagamento = cadastroFormaDePagamento.buscarOuFalhar(formaPagamentoId);

    restaurante.removerFormaPagamento(formaDePagamento);
  }

  @Transactional
  public void associarResponsavel(Long restauranteId, Long usuarioId) {
    var restaurante = buscarOuFalhar(restauranteId);
    var usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

    restaurante.adicionarResponsavel(usuario);
  }


  @Transactional
  public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
    var restaurante = buscarOuFalhar(restauranteId);
    var usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

    restaurante.removerResponsavel(usuario);
  }

  @Transactional
  public void ativar(Long restauranteId) {
    var restauranteAtual = buscarOuFalhar(restauranteId);

    restauranteAtual.ativar();
  }

  @Transactional
  public void inativar(Long restauranteId) {
    var restauranteAtual = buscarOuFalhar(restauranteId);

    restauranteAtual.inativar();
  }

  @Transactional
  public void ativar(List<Long> restauranteIds) {
    restauranteIds.forEach(this::ativar);
  }

  @Transactional
  public void inativar(List<Long> restauranteIds) {
    restauranteIds.forEach(this::inativar);
  }

  @Transactional
  public void abrir(Long restauranteId) {
    var restauranteAtual = buscarOuFalhar(restauranteId);

    restauranteAtual.abrir();
  }

  @Transactional
  public void fechar(Long restauranteId) {
    var restauranteAtual = buscarOuFalhar(restauranteId);

    restauranteAtual.fechar();
  }

  public Restaurante buscarOuFalhar(Long restauranteId) {
    return restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
  }
}
