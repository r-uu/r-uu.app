package de.ruu.app.demo.jpa.se;

import de.ruu.app.datamodel.company.jpa.DepartmentEntity;
import de.ruu.app.datamodel.company.jpadto.DepartmentServiceJPA;
import de.ruu.lib.jpa.se.Transactional;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Singleton
@Transactional
@Slf4j
public class DepartmentServiceJPAEditionSE implements DepartmentServiceJPA
{
	@Inject private DepartmentRepository repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public DepartmentEntity save  (DepartmentEntity entity) { return repository.save(entity); }
	@Override public DepartmentEntity           update(DepartmentEntity entity) { return repository.save(entity); }
	@Override public Optional<DepartmentEntity> find  (Long id)                 { return repository.find(id);     }
	@Override public void                       delete(Long id)                 {        repository.delete(id);   }
}