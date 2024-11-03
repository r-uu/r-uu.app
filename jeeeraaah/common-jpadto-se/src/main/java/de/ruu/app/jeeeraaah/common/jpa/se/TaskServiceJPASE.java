package de.ruu.app.jeeeraaah.common.jpa.se;

import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskServiceJPA;
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
public class TaskServiceJPASE implements TaskServiceJPA
{
	@Inject private TaskRepositoryJPASE repository;

	@PostConstruct private void postConstruct() { log.debug("injected repository: {}", repository); }

	@Override public TaskEntityJPA create(TaskEntityJPA entity) { return repository.save(entity); }
	@Override public Optional<TaskEntityJPA> read  (Long id          ) { return repository.find(id);     }
	@Override public TaskEntityJPA update(TaskEntityJPA entity) { return repository.save(entity); }
	@Override public void                 delete(Long id          ) {        repository.delete(id);   }

	@Override public Set<TaskEntityJPA> findAll() { return new HashSet<>(repository.findAll()); }
}