package org.acme.quarkus.sample.domain.gateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;

import org.acme.quarkus.sample.data.db.repository.IRepository;
import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;

public interface IGateway<E extends PanacheEntityBase, I> {

	public IRepository<E,I> getRepository();
	
	default void save(E object) {
		getRepository().persistAndFlush(object);
	}
	
	default Optional<E> getById(Map<String, Object> params){
		
		String queryId = params.keySet().stream()
				.map(key -> key + " = :" + key)
				.collect(Collectors.joining(" AND "));
		
		return getRepository().find(queryId, params).singleResultOptional();
		
	}
	
	default Pagination<E> getAll(PageRequest page){
		return getRepository().find(page);
	}
	
	//exemplo query específica select
    default Pagination<E> getAllByStartingLetter(PageRequest page){
    	
    	String teste = "'B%'";
		
		String query ="name LIKE " + teste;
		
		return getRepository().findMyQuery(page, query);
	}
	
}
