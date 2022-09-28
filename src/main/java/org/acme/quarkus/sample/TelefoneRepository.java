package org.acme.quarkus.sample;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepositoryBase<TelefoneEntity, Integer>{
	
	  public List<TelefoneEntity> findByInstrutor(InstrutorEntity i){
	        return find("instrutor", i).list();
	    }
	  
	  

}
