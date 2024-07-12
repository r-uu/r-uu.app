package de.ruu.app.datamodel.postaladdress.jpa.se;

import de.ruu.app.datamodel.postaladdress.jpa.PostalAddressEntity;
import de.ruu.lib.jpa.core.AbstractRepository;
import de.ruu.lib.jpa.se.Transactional;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Transactional
@Slf4j
public class PostalAddressRepositoryJPASE extends AbstractRepository<PostalAddressEntity, Long>
{
	@Inject private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }
}