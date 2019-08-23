package br.com.matrix.sgc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.repository.Motoristas;

@FacesConverter(forClass = Motorista.class)
public class MotoristaConverter implements Converter {

	@Inject
	private Motoristas motoristas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Motorista retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = motoristas.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Motorista motorista = (Motorista) value;
			return motorista.getId() == null ? null : motorista.getId().toString();
		}
		
		return "";
	}

}