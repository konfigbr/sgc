package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.repository.Motoristas;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroMotoristaService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Motoristas motoristas;
	
	@Transactional
	public Motorista salvar(Motorista motorista) throws NegocioException {
		return motoristas.guardar(motorista);
	}
}