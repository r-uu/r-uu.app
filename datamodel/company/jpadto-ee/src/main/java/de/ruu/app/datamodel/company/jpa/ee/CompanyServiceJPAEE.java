package de.ruu.app.datamodel.company.jpa.ee;

import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.CompanyRepository;
import de.ruu.app.datamodel.company.jpa.CompanyServiceJPA;
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
public class CompanyServiceJPAEE implements CompanyServiceJPA
{
	@Inject
	private CompanyRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public @NonNull          CompanyEntity  create(@NonNull CompanyEntity entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<CompanyEntity> read  (@NonNull Long          id    ) { return repository.find  (id    ); }
	@Override public @NonNull          CompanyEntity  update(@NonNull CompanyEntity entity) { return repository.save  (entity); }
	@Override public          void                    delete(@NonNull Long          id    ) {        repository.delete(id    ); }

	@Override public @NonNull Set<CompanyEntity> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public @NonNull Optional<CompanyEntity> findWithDepartments(Long id) { return repository.findWithDepartments(id); }
}