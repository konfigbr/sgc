package br.com.matrix.sgc.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Fornecedor;
import br.com.matrix.sgc.model.TipoPessoa;
import br.com.matrix.sgc.service.CadastroFornecedorService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fornecedor fornecedor;

	@Inject
	private CadastroFornecedorService cadastroFornecedorService;

	public void inicializar() {
		if (fornecedor == null) {
			limpar();
		}
	}

	public void limpar() {
		this.fornecedor = new Fornecedor();
		this.fornecedor.setTipo(TipoPessoa.FISICA);
	}

	public void salvar() {
		try {
			cadastroFornecedorService.salvar(fornecedor);
			limpar();

			FacesUtil.addInfoMessage("Fornecedor salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isEditando() {
		return fornecedor != null && fornecedor.getId() == null;
	}

	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();
	}
}
