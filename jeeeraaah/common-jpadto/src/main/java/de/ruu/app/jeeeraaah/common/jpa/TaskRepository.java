package de.ruu.app.jeeeraaah.common.jpa;

import de.ruu.lib.jpa.core.AbstractRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class TaskRepository extends AbstractRepository<TaskEntityJPA, Long>
{
	@PersistenceContext(name = "jeeeraaah") private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }
}