package de.ruu.app.jeeeraaah.common;

import java.util.Optional;
import java.util.Set;

public interface TaskService<T extends Task>
{
	T           create(T department);
	Optional<T> read  (Long id);
	T           update(T department);
	void        delete(Long id);

	Set<T> findAll();
}