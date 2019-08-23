package br.com.matrix.sgc.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.matrix.sgc.model.Cliente;
import br.com.matrix.sgc.repository.Clientes;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroContasReceberService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException {
		return clientes.guardar(cliente);
	}
}