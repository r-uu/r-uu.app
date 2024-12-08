package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntityJPA;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
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

@Slf4j
@Mapper
public abstract class Map_Task_JPA_DTO
{
	public final static Map_Task_JPA_DTO INSTANCE = Mappers.getMapper(Map_Task_JPA_DTO.class);

	private static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskEntityJPA map(@NonNull TaskEntityDTO input);
	public abstract @NonNull TaskEntityDTO map(@NonNull TaskEntityJPA input);

	public Optional<TaskEntityJPA> getFromContext(TaskEntityDTO dto   ) { return Optional.ofNullable(CONTEXT.get(dto   , TaskEntityJPA.class)); }
	public Optional<TaskEntityDTO> getFromContext(TaskEntityJPA entity) { return Optional.ofNullable(CONTEXT.get(entity, TaskEntityDTO.class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityJPA source, @MappingTarget TaskEntityDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskEntityJPA source, @MappingTarget TaskEntityDTO target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityDTO source, @MappingTarget TaskEntityJPA target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskEntityDTO source, @MappingTarget TaskEntityJPA target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	@ObjectFactory
	@NonNull TaskEntityJPA lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		TaskEntityJPA result = CONTEXT.get(input, TaskEntityJPA.class);
		if (result == null)
		{
			TaskGroupEntityJPA taskGroupEntity = Map_TaskGroup_JPA_DTO.INSTANCE.lookupOrCreate(input.taskGroup());
			result = new TaskEntityJPA(taskGroupEntity, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull TaskEntityDTO lookupOrCreate(@NonNull TaskEntityJPA input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO taskGroupDTO = Map_TaskGroup_JPA_DTO.INSTANCE.lookupOrCreate(input.taskGroup());
			result = new TaskEntityDTO(taskGroupDTO, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}