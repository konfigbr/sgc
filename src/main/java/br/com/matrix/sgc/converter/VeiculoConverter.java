package br.com.matrix.sgc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.Veiculos;

@FacesConverter(forClass = Veiculo.class)
public class VeiculoConverter implements Converter {

	@Inject
	private Veiculos veiculos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Veiculo retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = veiculos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Veiculo veiculo = (Veiculo) value;
			return veiculo.getId() == null ? null : veiculo.getId().toString();
		}
		
		return "";
	}

}