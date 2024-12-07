package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

/** {@link TaskEntityDTO} <-> {@link TaskDTO} */
@Slf4j
@Mapper
abstract class Map_Task_EntityDTO_DTO
{
	public final static Map_Task_EntityDTO_DTO INSTANCE = Mappers.getMapper(Map_Task_EntityDTO_DTO.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskDTO       map(@NonNull TaskEntityDTO source);
	public abstract @NonNull TaskEntityDTO map(@NonNull TaskBean      source);

	Optional<TaskDTO>       getFromContext(TaskEntityDTO dto) { return Optional.ofNullable(CONTEXT.get(dto, TaskDTO      .class)); }
	Optional<TaskEntityDTO> getFromContext(TaskDTO       dto) { return Optional.ofNullable(CONTEXT.get(dto, TaskEntityDTO.class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityDTO source, @MappingTarget TaskDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskEntityDTO source, @MappingTarget TaskDTO target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}
}