package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.Veiculos;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroVeiculoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Veiculos veiculos;
	
	@Transactional
	public Veiculo salvar(Veiculo veiculo) throws NegocioException {
		return veiculos.guardar(veiculo);
	}
}