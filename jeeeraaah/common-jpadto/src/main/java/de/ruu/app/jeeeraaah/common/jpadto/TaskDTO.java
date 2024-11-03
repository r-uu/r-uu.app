package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.app.jeeeraaah.common.TaskGroup;
import de.ruu.lib.jpa.core.Entity;

public interface TaskDTO<TG extends TaskGroupDTO<    T>,
                         T  extends TaskDTO     <TG, T>>
		extends Task<TaskGroup<T>, T>, Entity<Long> { }