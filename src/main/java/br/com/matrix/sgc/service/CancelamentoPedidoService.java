package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.Pedido;
import br.com.matrix.sgc.model.StatusPedido;
import br.com.matrix.sgc.repository.Pedidos;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
		
	@Transactional
	public Pedido cancelar(Pedido pedido) throws NegocioException {
		pedido = this.pedidos.porId(pedido.getId());
		
		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado no status "
					+ pedido.getStatus().getDescricao() + ".");
		}
		
				
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}

}
