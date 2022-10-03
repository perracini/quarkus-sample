package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.quarkus.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class DependenteService {

	private final DependenteConverter dependenteConverter;
	private final DependenteRepository dependenteRepository;
	private final TitularRepository titularRepository;

	@Transactional
	public List<DependenteEntity> saveList(@Valid List<Dependente> dependentes, TitularEntity titular) {
		log.debug("Saving Dependentes: {}", dependentes);
		List<DependenteEntity> dependentesEntity = new ArrayList<>();
		for (Dependente d : dependentes) {
			DependenteEntity de = dependenteConverter.dtoToEntity(d);
			titular.addDependente(de);
			dependenteRepository.persist(de);
			dependentesEntity.add(de);
		}
		return dependentesEntity;
	}

	public Optional<Dependente> findById(@NonNull Integer dependenteId) {
		Optional<DependenteEntity> entity = dependenteRepository.findByIdOptional(dependenteId);
		return entity.map(element -> dependenteConverter.entityToDto(element));
	}

	public List<Dependente> converterList(List<DependenteEntity> dependentesEntity) {
		List<Dependente> dependentes = new ArrayList<>();
		for (DependenteEntity d : dependentesEntity) {
			Dependente de = dependenteConverter.entityToDto(d);
			dependentes.add(de);
		}
		return dependentes;
	}

	@Transactional
	public DependenteEntity save(@Valid Dependente dependente) {
		log.debug("Saving Dependente: {}", dependente);
		DependenteEntity entity = dependenteConverter.dtoToEntity(dependente);
		TitularEntity titular = titularRepository.findById(dependente.getTitularId());
		titular.addDependente(entity);
		dependenteRepository.persist(entity);
		return entity;
	}

	@Transactional
	public void updateDependentesFromTitular(@Valid Titular titular) {
		TitularEntity entity = titularRepository.findByIdOptional(titular.getTitularId()).orElseThrow(
				() -> new ServiceException("No Titular found for titularId[%s]", titular.getTitularId()));
		
		List<DependenteEntity> toRemove = createRemoveListWhenUpdatingTitular(titular, entity);
		updateDependentes(titular, entity);
		removeDependentesFromTitular(toRemove);

	}
	
	private List<DependenteEntity> createRemoveListWhenUpdatingTitular(Titular titular, TitularEntity entity){
		List<DependenteEntity> toCompare = dependenteRepository.findByTitular(entity);
		List<DependenteEntity> toRemove = new ArrayList<>();
		log.debug("Remover: {}", toRemove.size());
		for(DependenteEntity ent : toCompare) {
			boolean removing = true;
			for (Dependente dep : titular.getDependentes()) {
				if(ent.getDependenteId().equals(dep.getDependenteId())) {
					removing = false;
				}
			}
			if(removing) {
				toRemove.add(ent);
			}
		}
		return toRemove;
	}
	
	private void updateDependentes(Titular dto, TitularEntity entity) {
		dto.getDependentes().forEach(dependente -> {
			log.debug("Updating Dependente: {}", dependente);
			DependenteEntity depEntity = new DependenteEntity();
			if(dependente.getDependenteId() != null) {
				depEntity = dependenteRepository.findById(dependente.getDependenteId());
			}
			depEntity.setName(dependente.getName());
			entity.addDependente(depEntity);
			dependenteRepository.persist(depEntity);	
		});
	}
	
	private void removeDependentesFromTitular(List<DependenteEntity> toRemove) {
		toRemove.forEach(rem -> {
			dependenteRepository.delete(rem);
		});
	}

}
