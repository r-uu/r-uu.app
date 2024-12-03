package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

/**
 * Generic interface for {@link TaskGroup}s that are DTOs. {@link TaskGroupDTO}s pair with {@link TaskGroupEntity}s and
 * are useful to transfer {@link TaskGroupEntity} state over (process) boundaries and back.
 *
 * @param <T>
 */
public interface TaskGroupDTO<T extends TaskDTO<? extends TaskGroupDTO<T>, T>>
		extends TaskGroup<T>, Entity<Long>
{ }