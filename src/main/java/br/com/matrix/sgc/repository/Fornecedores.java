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
import br.com.matrix.sgc.model.Fornecedor;
import br.com.matrix.sgc.repository.filter.FornecedorFilter;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jpa.Transactional;

public class Fornecedores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Fornecedor porId(Long id) {
		return this.manager.find(Fornecedor.class, id);
	}
	
	public List<Fornecedor> fornecedores(){
		return this.manager.createQuery("from Fornecedor",Fornecedor.class)
				.getResultList();
	}
	
	public List<Fornecedor> porNome(String nome) {
		return this.manager.createQuery("from Fornecedor " +
				"where upper(nome) like :nome", Fornecedor.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	public List<Fornecedor> filtrados(FornecedorFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> criteriaQuery = builder.createQuery(Fornecedor.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Fornecedor> fornecedorRoot = criteriaQuery.from(Fornecedor.class);
		
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			predicates.add(builder.equal(fornecedorRoot.get("documentoReceitaFederal"), filtro.getDocumentoReceitaFederal()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(fornecedorRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(fornecedorRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(fornecedorRoot.get("nome")));
		
		TypedQuery<Fornecedor> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public Fornecedor guardar(Fornecedor fornecedor) {
		return manager.merge(fornecedor);
	}	
	
	@Transactional
	public void remover(Fornecedor fornecedor) throws NegocioException {
		try {
			fornecedor = porId(fornecedor.getId());
			manager.remove(fornecedor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Fornecedor não pode ser excluído.");
		}
	}
}