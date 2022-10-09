package org.acme.quarkus.sample;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.acme.quarkus.exception.ServiceException;

import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class TitularService {

	private final TitularConverter titularConverter;
	private final TitularRepository titularRepository;

	@Transactional
	public TitularEntity save(@Valid Titular titular) {
		log.debug("Saving Titular: {}", titular);
		TitularEntity entity = titularConverter.dtoToEntity(titular);
		titularRepository.persist(entity);
		return entity;
	}

	public Optional<Titular> findById(@NonNull Integer titularId) {
		TitularEntity entity = titularRepository.findByIdOptional(titularId).orElseThrow(
				() -> new ServiceException("No Titular found for titularId[%s]", titularId));
		
		log.debug("tamanho da lista dependentes: {}", entity.getDependentes().size());
		
		Optional<Titular> titular = Optional.of(titularConverter.entityToDto(entity));
		return titular;
	}

	public Titular converter(Optional<TitularEntity> entity) {
		Titular titular = null;
		if (entity.isPresent()) {
			titular = titularConverter.entityToDto(entity.get());
		}
		return titular;
	}

	@Transactional
	public void delete(Integer titularId) {
		Optional<TitularEntity> entity = titularRepository.findByIdOptional(titularId);
		if (!entity.isPresent()) {
			throw new NotFoundException();
		}
		titularRepository.delete(entity.get());
	}

	@Transactional
	public void update(@Valid Titular titular) {
		log.debug("Updating Titular: {}", titular);
		if (Objects.isNull(titular.getTitularId())) {
			throw new ServiceException("Titular does not have an titularId");
		}
		TitularEntity entity = titularRepository.findByIdOptional(titular.getTitularId())
				.orElseThrow(() -> new ServiceException("No Titular found for titularId[%s]", titular.getTitularId()));
		entity = titularConverter.dtoToEntity(titular, entity);// este método não mexe na lista de dependentes pois já foi setada corretamente na entidade
		titularRepository.persist(entity);
	}

	public List<Titular> findAll() {
		return titularRepository.findAll().list().stream().map( t -> titularConverter.entityToDto(t))
				.collect(Collectors.toList());//aqui se remover a conversão podemos ver o id retornando por conta da anotação na entidade Dependente sJsonIdentityInfo
	}
}
