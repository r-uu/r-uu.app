package de.ruu.app.demo.common.jpa.ee;

import de.ruu.app.demo.common.datamodel.jpa.CompanyEntity;
import de.ruu.lib.jpa.core.AbstractRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class CompanyRepository extends AbstractRepository<CompanyEntity, Long>
{
	@PersistenceContext(name = "company") private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }
}