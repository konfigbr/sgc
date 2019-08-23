package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Fornecedor;
import br.com.matrix.sgc.repository.Fornecedores;
import br.com.matrix.sgc.repository.filter.FornecedorFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFornecedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Fornecedores fornecedores;
	
	private FornecedorFilter filtro;
	private List<Fornecedor> fornecedoresFiltrados;
	
	private Fornecedor fornecedorSelecionado;
	
	public PesquisaFornecedoresBean() {
		filtro = new FornecedorFilter();
	}
	
	public void pesquisar() {
		fornecedoresFiltrados = fornecedores.filtrados(filtro);
	}
	
	public void excluir() {
		try {
			fornecedores.remover(fornecedorSelecionado);
			fornecedoresFiltrados.remove(fornecedorSelecionado);
			
			FacesUtil.addInfoMessage("Fornecedor " + fornecedorSelecionado.getNome() + 
					" excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<Fornecedor> getFornecedoresFiltrados() {
		return fornecedoresFiltrados;
	}

	public FornecedorFilter getFiltro() {
		return filtro;
	}

	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}

	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}
	
}
