package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.BiMappedSource;
import lombok.NonNull;

public class TaskGroupDTO
		extends TaskGroupEntityDTO
		implements BiMappedSource<TaskGroupBean>
{
	public TaskGroupDTO(@NonNull String name) { super(name); }

	@Override public void beforeMapping(@NonNull TaskGroupBean taskGroup)
	{
		mapIdAndVersion(taskGroup);
		taskGroup.tasks().ifPresent(vs -> vs.forEach(v -> addTask(Mapper.INSTANCE.lookupOrCreate(v))));
	}
	@Override public void afterMapping (@NonNull TaskGroupBean input) { }

	@Override public @NonNull TaskGroupBean toTarget() { return Mapper.INSTANCE.map(this); }
}