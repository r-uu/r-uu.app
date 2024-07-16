package de.ruu.app.datamodel.company.jpa.se;

import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentServiceJPA;
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
public class DepartmentServiceJPASE implements DepartmentServiceJPA
{
	@Inject private DepartmentRepositoryJPASE repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public          DepartmentEntity  create(DepartmentEntity entity) { return repository.save(entity); }
	@Override public Optional<DepartmentEntity> read  (Long id)                 { return repository.find(id);     }
	@Override public          DepartmentEntity  update(DepartmentEntity entity) { return repository.save(entity); }
	@Override public void                       delete(Long id)                 {        repository.delete(id);   }

	@Override public Set<DepartmentEntity> findAll() { return new HashSet<>(repository.findAll()); }
}