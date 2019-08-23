package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.Veiculos;
import br.com.matrix.sgc.repository.filter.VeiculoFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaVeiculosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Veiculos veiculos;
	
	private VeiculoFilter filtro;
	private List<Veiculo> veiculosFiltrados;
	
	private Veiculo veiculoSelecionado;
	
	public PesquisaVeiculosBean() {
		filtro = new VeiculoFilter();
	}
	
	public void pesquisar() {
		veiculosFiltrados = veiculos.filtrados(filtro);
	}
	
	public void excluir() {
		try {
			veiculos.remover(veiculoSelecionado);
			veiculosFiltrados.remove(veiculoSelecionado);
			
			FacesUtil.addInfoMessage("Veiculos " + veiculoSelecionado.getPlacaCarreta() + 
					" excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<Veiculo> getVeiculosFiltrados() {
		return veiculosFiltrados;
	}

	public VeiculoFilter getFiltro() {
		return filtro;
	}

	public Veiculo getVeiculoSelecionado() {
		return veiculoSelecionado;
	}

	public void setVeiculoSelecionado(Veiculo veiculoSelecionado) {
		this.veiculoSelecionado = veiculoSelecionado;
	}
	
}
