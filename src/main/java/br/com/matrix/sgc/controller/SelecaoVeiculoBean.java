package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.Veiculos;

@Named
@ViewScoped
public class SelecaoVeiculoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Veiculos veiculos;
	
	private String placaCarreta;
	
	private List<Veiculo> veiculosFiltrados;
	
	public void pesquisar() {
		veiculosFiltrados = veiculos.porPlaca(placaCarreta);
	}

	public void selecionar(Veiculo veiculo) {
		RequestContext.getCurrentInstance().closeDialog(veiculo);
	}
	
	public void abrirDialogo() {
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("height", 470);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/SelecaoVeiculo", opcoes, null);
	}
	
	public String getPlacaCarreta() {
		return placaCarreta;
	}

	public void setPlacaCarreta(String placaCarreta) {
		this.placaCarreta = placaCarreta;
	}

	public List<Veiculo> getVeiculosFiltrados() {
		return veiculosFiltrados;
	}

}