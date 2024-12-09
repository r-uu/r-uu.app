package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/** {@link TaskGroupEntityDTO} -> {@link TaskGroupDTO} */
@Slf4j
@Mapper
public abstract class Map_TaskGroup_DTO_EntityDTO
{
	public final static Map_TaskGroup_DTO_EntityDTO INSTANCE = Mappers.getMapper(Map_TaskGroup_DTO_EntityDTO.class);

	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupDTO input);

	@ObjectFactory @NonNull TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupDTO input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskGroupEntityDTO(input);
	}
}