package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private CadastroUsuarioService cadastroUsuario;

  @Autowired
  private CadastroProdutoService cadastroProduto;

  @Autowired
  private CadastroCidadeService cadastroCidade;

  @Autowired
  private CadastroRestauranteService cadastroRestaurante;

  @Autowired
  private CadastroFormaDePagamentoService formaDePagamentoService;

  @Transactional
  public Pedido salvar(Pedido pedido) {

    validarItens(pedido);
    validarPedido(pedido);

    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
    pedido.calcularValorTotal();

    return pedidoRepository.save(pedido);
  }

  private void validarItens(Pedido pedido) {
    pedido.getItens().forEach(item -> {
      var produto = cadastroProduto.buscarOuFalhar(
              pedido.getRestaurante().getId(), item.getProduto().getId());

      item.setPedido(pedido);
      item.setProduto(produto);
      item.setPrecoUnitario(produto.getPreco());
    });
  }

  public void validarPedido(Pedido pedido) {
    var usuario = cadastroUsuario.buscarOuFalhar(1L);
    var restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
    var cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
    var formaDePagamento = formaDePagamentoService.buscarOuFalhar(pedido.getFormaDePagamento().getId());

    pedido.setCliente(usuario);
    pedido.getEnderecoEntrega().setCidade(cidade);
    pedido.setFormaDePagamento(formaDePagamento);
    pedido.setRestaurante(restaurante);

    if(restaurante.naoAceitaFormaDePagamento(formaDePagamento)){
      throw new NegocioException("Restaurante nao aceita " + formaDePagamento.getDescricao() + " como forma de pagamento!");
    }
  }

  public Pedido buscarOuFalhar(String codigoPedido) {
    return pedidoRepository.findByCodigo(codigoPedido)
            .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
  }
}