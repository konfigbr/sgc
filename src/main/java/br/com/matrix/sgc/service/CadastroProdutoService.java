package br.com.matrix.sgc.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.matrix.sgc.model.Produto;
import br.com.matrix.sgc.repository.Produtos;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;
	
	@Transactional
	public Produto salvar(Produto produto) throws NegocioException {
		Produto produtoExistente = produtos.porSku(produto.getSku());
		
		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado.");
		}
		
		return produtos.guardar(produto);
	}
	
}
