package de.ruu.app.jeeeraaah.common.jpadto.ee;

import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity_;
import de.ruu.lib.jpa.core.AbstractRepository;
import de.ruu.lib.jpa.core.GraphType;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@Slf4j
public class TaskGroupRepository extends AbstractRepository<TaskGroupEntity, Long>
{
	@PersistenceContext(name = "jeeeraaah_test") private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }

	public Optional<TaskGroupEntity> findWithTasks(@NonNull Long id)
	{
		EntityGraph<TaskGroupEntity> entityGraph = entityManager.createEntityGraph(TaskGroupEntity.class);
		entityGraph.addSubgraph(TaskGroupEntity_.tasks.getName());

		Map<String, Object> hints = new HashMap<>();
		hints.put(GraphType.FETCH.getName(), entityGraph);

		TaskGroupEntity result = entityManager.find(TaskGroupEntity.class, id, hints);

		return Optional.ofNullable(result);
	}
}