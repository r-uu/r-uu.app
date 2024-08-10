package de.ruu.app.datamodel.company.jpa.se;

import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.CompanyServiceJPA;
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
public class CompanyServiceJPASE implements CompanyServiceJPA
{
	@Inject private CompanyRepositoryJPASE repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public          CompanyEntity  create(CompanyEntity entity) { return repository.save(entity); }
	@Override public Optional<CompanyEntity> read  (Long          id)     { return repository.find  (id);   }
	@Override public          CompanyEntity  update(CompanyEntity entity) { return repository.save(entity); }
	@Override public void                    delete(Long          id)     {        repository.delete(id);   }

	@Override public Set<CompanyEntity> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public Optional<CompanyEntity> findWithDepartments(Long id) { return repository.findWithDepartments(id); }
}