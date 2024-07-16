package de.ruu.app.datamodel.postaladdress.jpa.se;

import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressServiceJPA;
import de.ruu.lib.jpa.se.Transactional;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
@Transactional
@Slf4j
public class PostalAddressServiceJPASE implements PostalAddressServiceJPA
{
	@Inject private PostalAddressRepositoryJPASE repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public          PostalAddressEntity  create(PostalAddressEntity entity) { return repository.save(entity); }
	@Override public Optional<PostalAddressEntity> read  (Long id)                    { return repository.find(id);     }
	@Override public          PostalAddressEntity  update(PostalAddressEntity address) { return repository.save(address); }
	@Override public void                          delete(Long id)                    {        repository.delete(id);   }

	@Override public Set<PostalAddressEntity> findAll() { return new HashSet<>(repository.findAll()); }
}