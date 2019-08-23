package br.com.matrix.sgc.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.LancamentoPagar;
import br.com.matrix.sgc.service.CadastroContasPagarService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroLancamentoPagarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LancamentoPagar lancamentoPagar;

	@Inject
	private CadastroContasPagarService cadastroContasPagarService;

	public void inicializar() {
		if (lancamentoPagar == null) {
			limpar();
		}
	}

	public void limpar() {
		this.lancamentoPagar = new LancamentoPagar();
	}

	public void salvar() {
		try {
			cadastroContasPagarService.salvar(lancamentoPagar);
			limpar();

			FacesUtil.addInfoMessage("Lançamento à Pagar salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public LancamentoPagar getLancamentoPagar() {
		return lancamentoPagar;
	}

	public void setLancamentoPagar(LancamentoPagar lancamentoPagar) {
		this.lancamentoPagar = lancamentoPagar;
	}

	public boolean isEditando() {
		return lancamentoPagar != null && lancamentoPagar.getId() == null;
	}

	
}
