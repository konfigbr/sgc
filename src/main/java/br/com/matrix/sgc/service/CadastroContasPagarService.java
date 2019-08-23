package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.LancamentoPagar;
import br.com.matrix.sgc.repository.LancamentosPagar;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroContasPagarService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentosPagar lancamentosPagar;
	
	@Transactional
	public LancamentoPagar salvar(LancamentoPagar lancamentoPagar) throws NegocioException {
		return lancamentosPagar.guardar(lancamentoPagar);
	}
}