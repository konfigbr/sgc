package br.com.matrix.sgc.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class LancamentoReceberFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private Date dataVencimento;
	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}