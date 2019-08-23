package br.com.matrix.sgc.controller;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.service.CadastroVeiculoService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroVeiculoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Veiculo veiculo;
	
	@Inject
	private CadastroVeiculoService cadastroVeiculoService;
	
	public void inicializar(){
		if (veiculo == null) {
			limpar();
		}
	}
	
	public void limpar() {
		this.veiculo = new Veiculo();		
	}
	
	public void salvar() {
		try {
			cadastroVeiculoService.salvar(veiculo);
			limpar();
			
			FacesUtil.addInfoMessage("Veiculo salvo com sucesso!");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public boolean isEditando() {
		return veiculo != null && veiculo.getId() == null;
	}
	
}
