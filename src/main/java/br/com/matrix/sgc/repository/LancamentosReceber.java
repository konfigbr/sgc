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
import br.com.matrix.sgc.model.LancamentoReceber;
import br.com.matrix.sgc.repository.filter.LancamentoReceberFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jpa.Transactional;

public class LancamentosReceber implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public LancamentoReceber porId(Long id) {
		return this.manager.find(LancamentoReceber.class, id);
	}
	
	public List<LancamentoReceber> porDescricao(String descricao) {
		return this.manager.createQuery("from LancamentoReceber " +
				"where upper(descricao) like :descricao", LancamentoReceber.class)
				.setParameter("descricao", descricao.toUpperCase() + "%")
				.getResultList();
	}
	
	public List<LancamentoReceber> filtrados(LancamentoReceberFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoReceber> criteriaQuery = builder.createQuery(LancamentoReceber.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<LancamentoReceber> lancamentoReceberRoot = criteriaQuery.from(LancamentoReceber.class);
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.equal(lancamentoReceberRoot.get("descricao"), filtro.getDescricao()));
		}
		
		if (filtro.getDataVencimento()!= null) {
			predicates.add(builder.greaterThanOrEqualTo(lancamentoReceberRoot.get("dataVencimento"),filtro.getDataVencimento()));
					
		}
		
		criteriaQuery.select(lancamentoReceberRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(lancamentoReceberRoot.get("dataVencimento")));
		
		TypedQuery<LancamentoReceber> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public LancamentoReceber guardar(LancamentoReceber lancamentoReceber) {
		return manager.merge(lancamentoReceber);
	}	
	
	@Transactional
	public void remover(LancamentoReceber lancamentoReceber) throws NegocioException {
		try {
			lancamentoReceber = porId(lancamentoReceber.getId());
			manager.remove(lancamentoReceber);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Lançamento à Receber não pode ser excluído.");
		}
	}
}