package de.ruu.app.datamodel.postaladdress.jpa.ee;

import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressServiceJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

	@Override public PostalAddressEntity create(PostalAddressEntity entity) { return repository.save  (entity); }
	@Override public Optional<PostalAddressEntity> read  (Long id)                    { return repository.find  (id);     }
	@Override public          PostalAddressEntity  update(PostalAddressEntity entity) { return repository.save  (entity); }
	@Override public void                          delete(Long id)                    {        repository.delete(id);     }

	@Override public Set<PostalAddressEntity> findAll() { return new HashSet<>(repository.findAll()); }
}