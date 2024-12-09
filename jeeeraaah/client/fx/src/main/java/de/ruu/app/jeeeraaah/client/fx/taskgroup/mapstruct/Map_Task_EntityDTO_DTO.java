package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskEntityDTO} <-> {@link TaskDTO} */
@Slf4j
@Mapper
abstract class Map_Task_EntityDTO_DTO
{
	public final static Map_Task_EntityDTO_DTO INSTANCE = Mappers.getMapper(Map_Task_EntityDTO_DTO.class);

	public abstract @NonNull TaskDTO map(@NonNull TaskEntityDTO source);

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

	@ObjectFactory @NonNull TaskDTO lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskDTO(input);
	}
}