package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity_;
import de.ruu.lib.jpa.core.AbstractRepository;
import de.ruu.lib.jpa.core.GraphType;
import de.ruu.lib.jpa.se.Transactional;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
@Transactional
@Slf4j
public class TaskGroupRepositoryJPASE extends AbstractRepository<TaskGroupEntity, Long>
{
	@Inject private EntityManager entityManager;

	@PostConstruct private void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }

	// TODO define in interface???
	public Optional<TaskGroupEntity> findWithTasks(@NonNull Long id)
	{
		EntityGraph<TaskGroupEntity> result = entityManager.createEntityGraph(TaskGroupEntity.class);
		result.addSubgraph(TaskGroupEntity_.tasks.getName());

		Map<String, Object> hints = new HashMap<>();
		hints.put(GraphType.FETCH.getName(), result);

		return Optional.ofNullable(entityManager.find(TaskGroupEntity.class, id, hints));
	}
}