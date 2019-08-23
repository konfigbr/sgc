package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.repository.Motoristas;
import br.com.matrix.sgc.repository.filter.MotoristaFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaMotoristasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Motoristas motoristas;
	
	private MotoristaFilter filtro;
	private List<Motorista> motoristasFiltrados;
	
	private Motorista motoristaSelecionado;
	
	public PesquisaMotoristasBean() {
		filtro = new MotoristaFilter();
	}
	
	public void pesquisar() {
		motoristasFiltrados = motoristas.filtrados(filtro);
	}
	
	public void excluir() {
		try {
			motoristas.remover(motoristaSelecionado);
			motoristasFiltrados.remove(motoristaSelecionado);
			
			FacesUtil.addInfoMessage("Motorista " + motoristaSelecionado.getNome() + 
					" excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<Motorista> getMotoristasFiltrados() {
		return motoristasFiltrados;
	}

	public MotoristaFilter getFiltro() {
		return filtro;
	}

	public Motorista getMotoristaSelecionado() {
		return motoristaSelecionado;
	}

	public void setMotoristaSelecionado(Motorista motoristaSelecionado) {
		this.motoristaSelecionado = motoristaSelecionado;
	}
	
}
