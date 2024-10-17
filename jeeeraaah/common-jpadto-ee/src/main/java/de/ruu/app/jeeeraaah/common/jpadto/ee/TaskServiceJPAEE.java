package de.ruu.app.jeeeraaah.common.jpadto.ee;

import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskServiceJPA;
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
public class TaskServiceJPAEE implements TaskServiceJPA
{
	@Inject
	private TaskRepository repository;

	@PostConstruct
	private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public @NonNull TaskEntity create(@NonNull TaskEntity entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<TaskEntity> read  (@NonNull Long id                   ) { return repository.find  (id    ); }
	@Override public @NonNull          TaskEntity  update(@NonNull TaskEntity entity) { return repository.save  (entity); }
	@Override public          void                          delete(@NonNull Long id                   ) {        repository.delete(id    ); }

	@Override public @NonNull Set<TaskEntity> findAll() { return new HashSet<>(repository.findAll()); }
}