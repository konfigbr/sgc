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
import br.com.matrix.sgc.model.LancamentoPagar;
import br.com.matrix.sgc.repository.filter.LancamentoPagarFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jpa.Transactional;

public class LancamentosPagar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public LancamentoPagar porId(Long id) {
		return this.manager.find(LancamentoPagar.class, id);
	}
	
	public List<LancamentoPagar> porDescricao(String descricao) {
		return this.manager.createQuery("from LancamentoPagar " +
				"where upper(descricao) like :descricao", LancamentoPagar.class)
				.setParameter("descricao", descricao.toUpperCase() + "%")
				.getResultList();
	}
	
	public List<LancamentoPagar> filtrados(LancamentoPagarFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoPagar> criteriaQuery = builder.createQuery(LancamentoPagar.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<LancamentoPagar> lancamentoPagarRoot = criteriaQuery.from(LancamentoPagar.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.equal(lancamentoPagarRoot.get("descricao"), filtro.getDescricao()));
		}
		
		if (filtro.getDataVencimento()!= null) {
			predicates.add(builder.greaterThanOrEqualTo(lancamentoPagarRoot.get("dataVencimento"),filtro.getDataVencimento()));
					
		}
		
		criteriaQuery.select(lancamentoPagarRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(lancamentoPagarRoot.get("dataVencimento")));
		
		TypedQuery<LancamentoPagar> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public LancamentoPagar guardar(LancamentoPagar lancamentoPagar) {
		return manager.merge(lancamentoPagar);
	}	
	
	@Transactional
	public void remover(LancamentoPagar lancamentoPagar) throws NegocioException {
		try {
			lancamentoPagar = porId(lancamentoPagar.getId());
			manager.remove(lancamentoPagar);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Lançamento à Pagar não pode ser excluído.");
		}
	}
}