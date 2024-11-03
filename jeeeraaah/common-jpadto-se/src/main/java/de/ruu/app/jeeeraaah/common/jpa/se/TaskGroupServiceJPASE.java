package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupServiceJPA;
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
public class TaskGroupServiceJPASE implements TaskGroupServiceJPA
{
	@Inject private TaskGroupRepositoryJPASE repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public TaskGroupEntityJPA create(TaskGroupEntityJPA entity) { return repository.save  (entity); }
	@Override public Optional<TaskGroupEntityJPA> read  (Long            id    ) { return repository.find  (id    ); }
	@Override public TaskGroupEntityJPA update(TaskGroupEntityJPA entity) { return repository.save  (entity); }
	@Override public void                      delete(Long            id    ) {        repository.delete(id    ); }

	@Override public Set<TaskGroupEntityJPA> findAll() { return new HashSet<>(repository.findAll()); }

	@Override public Optional<TaskGroupEntityJPA> findWithTasks(Long id) { return repository.findWithTasks(id); }
}