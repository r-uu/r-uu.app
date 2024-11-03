package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.BiMappedSource;
import lombok.NonNull;

public class TaskDTO
		extends TaskEntityDTO
		implements BiMappedSource<TaskBean>
{
	public TaskDTO(@NonNull TaskGroupEntityDTO taskGroup, @NonNull String name) { super(taskGroup, name); }

	@Override public void beforeMapping(@NonNull TaskBean input) { }
	@Override public void afterMapping (@NonNull TaskBean input) { }

	@Override public @NonNull TaskBean toTarget() { return Mapper.INSTANCE.map(this); }
}