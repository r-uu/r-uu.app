package de.ruu.app.datamodel.company.jpa.ee;

import de.ruu.app.datamodel.company.jpa.CompanyEntity;
import de.ruu.app.datamodel.company.jpa.CompanyRepository;
import de.ruu.app.datamodel.company.jpadto.CompanyServiceJPA;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Transactional
@Slf4j
public class CompanyServiceJPAEditionEE implements CompanyServiceJPA
{
	@Inject
	private CompanyRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public CompanyEntity           create(CompanyEntity entity) { return repository.save  (entity); }
	@Override public Optional<CompanyEntity> read  (Long id)              { return repository.find  (id);     }
	@Override public          CompanyEntity  update(CompanyEntity entity) { return repository.save  (entity); }
	@Override public void                    delete(Long id)              {        repository.delete(id);     }

	@Override public Set<CompanyEntity> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public Optional<CompanyEntity> findWithDepartments(Long id) { return repository.findWithDepartments(id); }
}