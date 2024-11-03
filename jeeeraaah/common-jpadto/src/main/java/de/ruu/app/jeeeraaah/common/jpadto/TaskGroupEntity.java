package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

public interface TaskGroupEntity<T extends TaskEntity<? extends TaskGroupEntity<T>, T>>
		extends TaskGroup<T>, Entity<Long>
{ }