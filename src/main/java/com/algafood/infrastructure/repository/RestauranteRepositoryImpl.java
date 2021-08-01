package com.algafood.infrastructure.repository;

import static com.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class); // from restaurante
		
		if (StringUtils.hasLength(nome)) {			
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if (taxaFreteInicial != null) {			
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if (taxaFreteFinal != null) {			
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
//		criteria.where(nomePredicate, taxaInicialPredicaete, taxaFinalPredicaete);
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis()
				.and(comNomeSemelhante(nome)));
	}

}
