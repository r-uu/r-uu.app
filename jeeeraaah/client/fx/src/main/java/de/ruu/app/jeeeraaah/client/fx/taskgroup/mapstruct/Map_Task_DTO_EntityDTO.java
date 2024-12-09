package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskEntityDTO} <-> {@link TaskDTO} */
@Slf4j
@Mapper
abstract class Map_Task_DTO_EntityDTO
{
	public final static Map_Task_DTO_EntityDTO INSTANCE = Mappers.getMapper(Map_Task_DTO_EntityDTO.class);

	public abstract @NonNull TaskEntityDTO map(@NonNull TaskBean source);

//	Optional<TaskDTO>       getFromContext(TaskEntityDTO dto) { return Optional.ofNullable(CONTEXT.get(dto, TaskDTO      .class)); }
//	Optional<TaskEntityDTO> getFromContext(TaskDTO       dto) { return Optional.ofNullable(CONTEXT.get(dto, TaskEntityDTO.class)); }

	@ObjectFactory @NonNull TaskDTO lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskDTO(input);
	}
}