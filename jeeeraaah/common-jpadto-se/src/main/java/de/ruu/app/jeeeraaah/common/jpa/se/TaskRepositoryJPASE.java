package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
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
class TaskRepositoryJPASE extends AbstractRepository<TaskEntity, Long>
{
	@Inject private EntityManager entityManager;

	@PostConstruct private void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }
}