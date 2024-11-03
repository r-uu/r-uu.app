package de.ruu.app.jeeeraaah.common.jpa;

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
public class TaskGroupRepository extends AbstractRepository<TaskGroupEntityJPA, Long>
{
	@PersistenceContext(name = "jeeeraaah") private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }

	public Optional<TaskGroupEntityJPA> findWithTasks(@NonNull Long id)
	{
		EntityGraph<TaskGroupEntityJPA> entityGraph = entityManager.createEntityGraph(TaskGroupEntityJPA.class);
		entityGraph.addSubgraph(TaskGroupEntityJPA_.tasks.getName());

		Map<String, Object> hints = new HashMap<>();
		hints.put(GraphType.FETCH.getName(), entityGraph);

		TaskGroupEntityJPA result = entityManager.find(TaskGroupEntityJPA.class, id, hints);

		return Optional.ofNullable(result);
	}
}