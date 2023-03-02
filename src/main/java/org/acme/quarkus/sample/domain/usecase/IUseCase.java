package org.acme.quarkus.sample.domain.usecase;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.PersistenceException;

import org.acme.quarkus.sample.domain.dto.request.PageRequest;
import org.acme.quarkus.sample.domain.dto.request.Pagination;
import org.acme.quarkus.sample.domain.gateway.IGateway;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@ApplicationScoped
public interface IUseCase<E extends PanacheEntityBase,I> {  //herdar do caso pai
	
	public IGateway<E,I> getGateway();
	
	default void save(E object) throws PersistenceException{
		getGateway().save(object);
	}
	
	default Optional<E> getById(Map<String, Object> id){
		return getGateway().getById(id);
	}
	
	default Pagination<E> getAll(PageRequest page){
		return getGateway().getAll(page);
	}
	
	default Pagination<E> getAllByStartingLetter(PageRequest page){
		return getGateway().getAllByStartingLetter(page);
	}
	
}
