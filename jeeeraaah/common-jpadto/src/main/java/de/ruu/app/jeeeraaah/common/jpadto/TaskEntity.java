package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

public interface TaskEntity<TG extends TaskGroupEntity<    T>,
                            T  extends TaskEntity     <TG, T>>
		extends Task<TaskGroup<T>, T>, Entity<Long>
{ }