package de.ruu.app.demo.common.jpa.ee;

import de.ruu.app.demo.common.datamodel.jpa.CompanyEntity;
import de.ruu.app.demo.common.datamodel.jpa.CompanyEntity_;
import de.ruu.app.demo.common.jpa.CompanyServiceJPA;
import de.ruu.lib.jpa.core.GraphType;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityGraph;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@ApplicationScoped
@Transactional
@Slf4j
public class CompanyServiceJPAEditionEE implements CompanyServiceJPA
{
	@Inject
	private CompanyRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public          CompanyEntity  create(CompanyEntity entity) { return repository.save  (entity); }
	@Override public Optional<CompanyEntity> read  (Long id)              { return repository.find  (id);     }
	@Override public          CompanyEntity  update(CompanyEntity entity) { return repository.save  (entity); }
	@Override public void                    delete(Long id)              {        repository.delete(id);     }

	@Override public Set<CompanyEntity> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public Optional<CompanyEntity> findWithDepartments(Long id)
	{
		EntityGraph<CompanyEntity> graph = repository.entityManager().createEntityGraph(CompanyEntity.class);
		graph.addSubgraph(CompanyEntity_.departments.getName());
		Map<String, Object> hints = new HashMap<>();
		hints.put(GraphType.FETCH.getName(), graph);
		return Optional.ofNullable(repository.entityManager().find(CompanyEntity.class, id, hints));
	}
}