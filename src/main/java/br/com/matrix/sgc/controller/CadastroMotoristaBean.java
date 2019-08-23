package br.com.matrix.sgc.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.model.TipoPessoa;
import br.com.matrix.sgc.service.CadastroMotoristaService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroMotoristaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Motorista motorista;

	@Inject
	private CadastroMotoristaService cadastroMotoristaService;

	public void inicializar() {
		if (motorista == null) {
			limpar();
		}
	}

	public void limpar() {
		this.motorista = new Motorista();
		this.motorista.setTipo(TipoPessoa.FISICA);
	}

	public void salvar() {
		try {
			cadastroMotoristaService.salvar(motorista);
			limpar();

			FacesUtil.addInfoMessage("Motorista salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public boolean isEditando() {
		return motorista != null && motorista.getId() == null;
	}

	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();
	}
}
