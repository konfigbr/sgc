package br.com.matrix.sgc.model;

public enum FormaPagamento {

	DINHEIRO("Dinheiro"), 
	BOLETO_BANCARIO("Boleto bancário"); 
		
	private String descricao;
	
	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}