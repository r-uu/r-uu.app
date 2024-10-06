package de.ruu.app.jeeeraaah.common;

import java.util.Optional;
import java.util.Set;

public interface TaskGroupService<TG extends TaskGroup>
{
	TG           create(TG   taskGroup);
	Optional<TG> read  (Long id       );
	TG           update(TG   taskGroup);
	void         delete(Long id       );

	Set<TG>      findAll();
	Optional<TG> findWithTasks(Long id);
}