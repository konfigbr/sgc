package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.LancamentoReceber;
import br.com.matrix.sgc.repository.LancamentosReceber;
import br.com.matrix.sgc.repository.filter.LancamentoReceberFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaLancamentosReceberBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentosReceber lancamentos;
	
	private LancamentoReceberFilter filtro;
	private List<LancamentoReceber> lancamentosFiltrados;
	
	private LancamentoReceber lancamentoSelecionado;
	
	public PesquisaLancamentosReceberBean() {
		filtro = new LancamentoReceberFilter();
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
	
	public List<LancamentoReceber> getLancamentosFiltrados() {
		return lancamentosFiltrados;
	}

	public LancamentoReceberFilter getFiltro() {
		return filtro;
	}

	public LancamentoReceber getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}

	public void setLancamentoSelecionado(LancamentoReceber lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}
	
}
