package br.com.matrix.sgc.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.matrix.sgc.model.ItemPedido;
import br.com.matrix.sgc.model.Pedido;
import br.com.matrix.sgc.repository.Pedidos;
import br.com.matrix.sgc.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) throws NegocioException {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
	
}
