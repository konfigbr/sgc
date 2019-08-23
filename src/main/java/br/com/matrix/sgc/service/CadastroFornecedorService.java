package br.com.matrix.sgc.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.matrix.sgc.model.Fornecedor;
import br.com.matrix.sgc.repository.Fornecedores;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroFornecedorService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Fornecedores fornecedores;
	
	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) throws NegocioException {
		return fornecedores.guardar(fornecedor);
	}
}