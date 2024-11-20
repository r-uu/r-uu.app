package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntityJPA;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

public abstract class Map_TaskGroup_JPA_DTO
{
	public final static Map_TaskGroup_JPA_DTO INSTANCE = Mappers.getMapper(Map_TaskGroup_JPA_DTO.class);

	private static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupEntityJPA map(@NonNull TaskGroupEntityDTO input);
	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupEntityJPA input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityJPA source, @MappingTarget TaskGroupEntityDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupEntityJPA source, @MappingTarget TaskGroupEntityDTO target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupEntityJPA target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupEntityJPA target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskGroupEntityJPA lookupOrCreate(@NonNull TaskGroupEntityDTO input)
	{
		TaskGroupEntityJPA result = CONTEXT.get(input, TaskGroupEntityJPA.class);
		if (result == null)
		{
			result = new TaskGroupEntityJPA(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupEntityJPA input)
	{
		TaskGroupEntityDTO result = CONTEXT.get(input, TaskGroupEntityDTO.class);
		if (result == null)
		{
			result = new TaskGroupEntityDTO(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}