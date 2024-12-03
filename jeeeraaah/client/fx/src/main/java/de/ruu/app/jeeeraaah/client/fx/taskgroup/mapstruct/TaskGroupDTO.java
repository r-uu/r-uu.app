package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.jpa.core.Entity;
import lombok.NoArgsConstructor;
import lombok.NonNull;

class TaskGroupDTO extends TaskGroupEntityDTO
{
	public TaskGroupDTO(@NonNull String name) { super(name); }

	// "closes" map struct callbacks from superclass
	/** @throws UnsupportedOperationException */
	@Override protected final void beforeMapping(@NonNull Entity<Long> input) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("use beforeMapping(TaskGroupEntityDTO arg) instead");
	}


	/** @throws UnsupportedOperationException */
	@Override protected final void afterMapping(@NonNull Entity<Long> input) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("use afterMapping(TaskGroupEntityDTO arg) instead");
	}

	// "opens" map struct callbacks from superclass
	void beforeMapping(@NonNull TaskGroupEntityDTO input) { super.beforeMapping(input); }
	void afterMapping (@NonNull TaskGroupEntityDTO input) { super.afterMapping (input); }
}