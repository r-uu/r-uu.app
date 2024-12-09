package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskGroupEntityDTO} -> {@link TaskGroupDTO} */
@Slf4j
@Mapper
public abstract class Map_TaskGroup_EntityDTO_DTO
{
	public final static Map_TaskGroup_EntityDTO_DTO INSTANCE = Mappers.getMapper(Map_TaskGroup_EntityDTO_DTO.class);

	public abstract @NonNull TaskGroupDTO map(@NonNull TaskGroupEntityDTO input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupEntityDTO source, @MappingTarget TaskGroupDTO target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	@ObjectFactory @NonNull TaskGroupDTO lookupOrCreate(@NonNull TaskGroupEntityDTO input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskGroupDTO(input);
	}
}