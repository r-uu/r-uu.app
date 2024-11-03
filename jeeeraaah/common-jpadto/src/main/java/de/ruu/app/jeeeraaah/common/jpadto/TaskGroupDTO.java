package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

public interface TaskGroupDTO<T extends TaskDTO<? extends TaskGroupDTO<T>, T>>
		extends TaskGroup<T>, Entity<Long>
{ }