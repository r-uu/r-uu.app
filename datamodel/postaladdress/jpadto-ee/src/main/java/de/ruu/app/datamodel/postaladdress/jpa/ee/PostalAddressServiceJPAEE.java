package de.ruu.app.datamodel.postaladdress.jpa.ee;

import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressServiceJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Transactional
@Slf4j
public class PostalAddressServiceJPAEE implements PostalAddressServiceJPA
{
	@Inject
	private PostalAddressRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public @NonNull          PostalAddressEntity  create(@NonNull PostalAddressEntity entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<PostalAddressEntity> read  (@NonNull Long id                   ) { return repository.find  (id    ); }
	@Override public @NonNull          PostalAddressEntity  update(@NonNull PostalAddressEntity entity) { return repository.save  (entity); }
	@Override public          void                          delete(@NonNull Long id                   ) {        repository.delete(id    ); }

	@Override public @NonNull Set<PostalAddressEntity> findAll() { return new HashSet<>(repository.findAll()); }
}