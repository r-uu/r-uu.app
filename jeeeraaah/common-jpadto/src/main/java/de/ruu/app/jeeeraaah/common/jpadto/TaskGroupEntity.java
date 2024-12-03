package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

/**
 * Generic interface for {@link TaskGroup}s that are {@link Entity}s. {@link TaskGroupEntity}s pair with {@link
 * TaskGroupDTO}s where DTOs are used to transfer {@link TaskGroupEntity} state over (process) boundaries and back.
 *
 * @param <T> {@link Task} implementation for {@link #tasks()} that belong to this task group instance
 */
public interface TaskGroupEntity<T extends TaskEntity<? extends TaskGroupEntity<T>, T>>
		extends TaskGroup<T>, Entity<Long>
{ }