package br.com.matrix.sgc.service;

import java.io.Serializable;
import javax.inject.Inject;
import br.com.matrix.sgc.model.LancamentoReceber;
import br.com.matrix.sgc.repository.LancamentosReceber;
import br.com.matrix.sgc.util.jpa.Transactional;

public class CadastroContasReceberService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentosReceber lancamentosReceber;
	
	@Transactional
	public LancamentoReceber salvar(LancamentoReceber lancamentoReceber) throws NegocioException {
		return lancamentosReceber.guardar(lancamentoReceber);
	}
}