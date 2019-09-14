package br.com.matrix.sgc.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.matrix.sgc.model.LancamentoReceber;
import br.com.matrix.sgc.service.CadastroContasReceberService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroLancamentoReceberBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LancamentoReceber lancamentoReceber;

	@Inject
	private CadastroContasReceberService cadastroContasReceberService;

	public void inicializar() {
		if (lancamentoReceber == null) {
			limpar();
		}
	}

	public void limpar() {
		this.lancamentoReceber = new LancamentoReceber();
	}

	public void salvar() {
		try {
			cadastroContasReceberService.salvar(lancamentoReceber);
			limpar();

			FacesUtil.addInfoMessage("Lançamento à Receber salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public LancamentoReceber getLancamentoReceber() {
		return lancamentoReceber;
	}

	public void setLancamentoReceber(LancamentoReceber lancamentoReceber) {
		this.lancamentoReceber = lancamentoReceber;
	}

	public boolean isEditando() {
		return lancamentoReceber != null && lancamentoReceber.getId() == null;
	}

	
}
