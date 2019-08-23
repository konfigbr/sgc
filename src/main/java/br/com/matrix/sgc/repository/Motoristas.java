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
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.repository.filter.MotoristaFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jpa.Transactional;

public class Motoristas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Motorista porId(Long id) {
		return this.manager.find(Motorista.class, id);
	}
	
	public List<Motorista> motoristas(){
		return this.manager.createQuery("from Motorista",Motorista.class)
				.getResultList();
	}
	
	public List<Motorista> porNome(String nome) {
		return this.manager.createQuery("from Motorista " +
				"where upper(nome) like :nome", Motorista.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	public List<Motorista> filtrados(MotoristaFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Motorista> criteriaQuery = builder.createQuery(Motorista.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Motorista> motoristaRoot = criteriaQuery.from(Motorista.class);
		
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			predicates.add(builder.equal(motoristaRoot.get("documentoReceitaFederal"), filtro.getDocumentoReceitaFederal()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(motoristaRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(motoristaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(motoristaRoot.get("nome")));
		
		TypedQuery<Motorista> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public Motorista guardar(Motorista motorista) {
		return manager.merge(motorista);
	}	
	
	@Transactional
	public void remover(Motorista motorista) throws NegocioException {
		try {
			motorista = porId(motorista.getId());
			manager.remove(motorista);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Motorista não pode ser excluído.");
		}
	}
}