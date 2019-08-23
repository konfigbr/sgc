package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.LancamentoPagar;
import br.com.matrix.sgc.repository.LancamentosPagar;
import br.com.matrix.sgc.repository.filter.LancamentoPagarFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaLancamentosPagarBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentosPagar lancamentos;
	
	private LancamentoPagarFilter filtro;
	private List<LancamentoPagar> lancamentosFiltrados;
	
	private LancamentoPagar lancamentoSelecionado;
	
	public PesquisaLancamentosPagarBean() {
		filtro = new LancamentoPagarFilter();
	}
	
	public void pesquisar() {
		lancamentosFiltrados = lancamentos.filtrados(filtro);
	}
	
	public void excluir() {
		try {
			lancamentos.remover(lancamentoSelecionado);
			lancamentosFiltrados.remove(lancamentoSelecionado);
			
			FacesUtil.addInfoMessage("Lançamento " + lancamentoSelecionado.getDescricao() + 
					" excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<LancamentoPagar> getLancamentosFiltrados() {
		return lancamentosFiltrados;
	}

	public LancamentoPagarFilter getFiltro() {
		return filtro;
	}

	public LancamentoPagar getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(LancamentoPagar lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}
	
}
