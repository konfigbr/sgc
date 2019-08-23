package br.com.matrix.sgc.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.filter.VeiculoFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jpa.Transactional;

public class Veiculos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Veiculo porId(Long id) {
		return this.manager.find(Veiculo.class, id);
	}
	
	public List<Veiculo> veiculos(){
		return this.manager.createQuery("from Veiculo",Veiculo.class)
				.getResultList();
	}
	
	public List<Veiculo> porPlaca(String placa) {
		return this.manager.createQuery("from Veiculo " +
				"where upper(placaCarreta) like :placa", Veiculo.class)
				.setParameter("placa", placa.toUpperCase() + "%")
				.getResultList();		
	}
	
	public List<Veiculo> filtrados(VeiculoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Veiculo> criteriaQuery = builder.createQuery(Veiculo.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Veiculo> veiculoRoot = criteriaQuery.from(Veiculo.class);
		
		
		if (StringUtils.isNotBlank(filtro.getPlacaCarreta())) {
			predicates.add(builder.like(builder.lower(veiculoRoot.get("placaCarreta")), 
					"%" + filtro.getPlacaCarreta().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(veiculoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(veiculoRoot.get("placaCarreta")));
		
		TypedQuery<Veiculo> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public Veiculo guardar(Veiculo veiculo) {
		return manager.merge(veiculo);
	}	
	
	@Transactional
	public void remover(Veiculo veiculo) throws NegocioException {
		try {
			veiculo = porId(veiculo.getId());
			manager.remove(veiculo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Veiculo não pode ser excluído.");
		}
	}
}