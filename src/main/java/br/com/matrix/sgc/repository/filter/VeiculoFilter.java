package br.com.matrix.sgc.repository.filter;

import java.io.Serializable;

public class VeiculoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String placaCarreta;

	public String getPlacaCarreta() {
		return placaCarreta;
	}

	public void setPlacaCarreta(String placa) {
		this.placaCarreta = placa;
	}

}