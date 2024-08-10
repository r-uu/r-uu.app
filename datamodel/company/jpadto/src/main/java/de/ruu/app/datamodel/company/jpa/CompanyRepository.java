package de.ruu.app.datamodel.company.jpa;

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
public class CompanyRepository extends AbstractRepository<CompanyEntity, Long>
{
	@PersistenceContext(name = "demo") private EntityManager entityManager;

	@PostConstruct public void postConstruct() { log.debug("injected entity manager successfully: {}", entityManager != null); }

	@Override protected EntityManager entityManager() { return entityManager; }

	public Optional<CompanyEntity> findWithDepartments(@NonNull Long id)
	{
		EntityGraph<CompanyEntity> entityGraph = entityManager.createEntityGraph(CompanyEntity.class);
		entityGraph.addSubgraph(CompanyEntity_.departments.getName());

		Map<String, Object> hints = new HashMap<>();
		hints.put(GraphType.FETCH.getName(), entityGraph);

		CompanyEntity result = entityManager.find(CompanyEntity.class, id, hints);

		return Optional.ofNullable(result);
	}
}