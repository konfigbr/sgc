package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.LancamentoReceber;
import br.com.matrix.sgc.model.Pedido;
import br.com.matrix.sgc.model.StatusPedido;
import br.com.matrix.sgc.repository.Pedidos;
import br.com.matrix.sgc.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private CadastroContasReceberService lancamentoService;
	
	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public Pedido emitir(Pedido pedido) throws NegocioException {
		pedido = this.cadastroPedidoService.salvar(pedido);
				
		if (pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		pedido = this.pedidos.guardar(pedido);
		
		LancamentoReceber lancamento = new LancamentoReceber();
		lancamento.setDescricao(pedido.getCliente().getNome() + " - " + pedido.getId());
		lancamento.setDataLancamento(pedido.getDataCriacao());
		lancamento.setDataVencimento(pedido.getDataCriacao());
		lancamento.setValor(pedido.getValorTotal());
		lancamento.setPedido(pedido);
		lancamentoService.salvar(lancamento);		
				
		return pedido;
	}
	
}
