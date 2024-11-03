package de.ruu.app.jeeeraaah.common.jpadto.ee;

import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
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

	@Override public @NonNull TaskEntityJPA create(@NonNull TaskEntityJPA entity) { return repository.save  (entity); }
	@Override public @NonNull Optional<TaskEntityJPA> read  (@NonNull Long id                   ) { return repository.find  (id    ); }
	@Override public @NonNull TaskEntityJPA update(@NonNull TaskEntityJPA entity) { return repository.save  (entity); }
	@Override public          void                          delete(@NonNull Long id                   ) {        repository.delete(id    ); }

	@Override public @NonNull Set<TaskEntityJPA> findAll() { return new HashSet<>(repository.findAll()); }
}