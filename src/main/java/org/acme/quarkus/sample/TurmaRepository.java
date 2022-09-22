package org.acme.quarkus.sample;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NamedQuery;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class TurmaRepository implements PanacheRepositoryBase<TurmaEntity, Integer> {
	
	  public List<TurmaEntity> findByTeste(LocalDate d){
	        return find("#teste", d).list();
	    }
	
}
