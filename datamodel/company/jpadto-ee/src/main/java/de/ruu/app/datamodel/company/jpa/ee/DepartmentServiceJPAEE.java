package de.ruu.app.datamodel.company.jpa.ee;

import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpa.DepartmentRepository;
import de.ruu.app.datamodel.company.jpa.DepartmentServiceJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Transactional
@Slf4j
public class DepartmentServiceJPAEE implements DepartmentServiceJPA
{
	@Inject
	private DepartmentRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public @NonNull          DepartmentEntity  create(@NonNull DepartmentEntity entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<DepartmentEntity> read  (@NonNull Long id                ) { return repository.find  (id    ); }
	@Override public @NonNull          DepartmentEntity  update(@NonNull DepartmentEntity entity) { return repository.save  (entity); }
	@Override public          void                       delete(@NonNull Long id                ) {        repository.delete(id    ); }

	@Override public @NonNull Set<DepartmentEntity> findAll() { return new HashSet<>(repository.findAll()); }
}