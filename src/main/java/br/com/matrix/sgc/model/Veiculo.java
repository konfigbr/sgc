package br.com.matrix.sgc.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "veiculo")
public class Veiculo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String placaCarreta;
	private String placaCavalo;
	private String capacidade;
	private String compartimentos;
	private String lacres;
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank
	@Size(max = 20)
	@Column(name="placa_carreta",nullable = false, length = 20)
	public String getPlacaCarreta() {
		return placaCarreta;
	}

	public void setPlacaCarreta(String placaCarreta) {
		this.placaCarreta = placaCarreta;
	}

	@NotBlank
	@Column(name="placa_cavalo",nullable = false, length = 20)
	public String getPlacaCavalo() {
		return placaCavalo;
	}

	public void setPlacaCavalo(String placaCavalo) {
		this.placaCavalo = placaCavalo;
	}

	public String getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(String capacidade) {
		this.capacidade = capacidade;
	}	

	public String getCompartimentos() {
		return compartimentos;
	}

	public void setCompartimentos(String compartimentos) {
		this.compartimentos = compartimentos;
	}

	public String getLacres() {
		return lacres;
	}

	public void setLacres(String lacres) {
		this.lacres = lacres;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
