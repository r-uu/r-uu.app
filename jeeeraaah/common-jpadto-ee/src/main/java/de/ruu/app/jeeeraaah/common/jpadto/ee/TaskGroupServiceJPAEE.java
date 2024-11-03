package de.ruu.app.jeeeraaah.common.jpadto.ee;

import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupServiceJPA;
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
public class TaskGroupServiceJPAEE implements TaskGroupServiceJPA
{
	@Inject
	private TaskGroupRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public @NonNull TaskGroupEntityJPA create(@NonNull TaskGroupEntityJPA entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<TaskGroupEntityJPA> read  (@NonNull Long id                   ) { return repository.find  (id    ); }
	@Override public @NonNull TaskGroupEntityJPA update(@NonNull TaskGroupEntityJPA entity) { return repository.save  (entity); }
	@Override public          void                          delete(@NonNull Long id                   ) {        repository.delete(id    ); }

	@Override public @NonNull Set<TaskGroupEntityJPA> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public Optional<TaskGroupEntityJPA> findWithTasks(Long id) { return repository.findWithTasks(id); }
}