package br.com.matrix.sgc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import br.com.matrix.sgc.model.LancamentoPagar;
import br.com.matrix.sgc.repository.LancamentosPagar;

@FacesConverter(forClass = LancamentoPagar.class)
public class LancamentoPagarConverter implements Converter {

	@Inject
	private LancamentosPagar lancamentosPagar;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		LancamentoPagar retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = lancamentosPagar.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			LancamentoPagar lancamentoPagar = (LancamentoPagar) value;
			return lancamentoPagar.getId() == null ? null : lancamentoPagar.getId().toString();
		}
		
		return "";
	}

}