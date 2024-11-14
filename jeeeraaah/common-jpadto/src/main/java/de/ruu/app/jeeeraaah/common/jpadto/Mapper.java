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
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Slf4j
@org.mapstruct.Mapper
public abstract class Mapper
{
	public final static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupEntityJPA map(@NonNull TaskGroupEntityDTO input);
	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupEntityJPA input);

	public abstract @NonNull TaskEntityJPA map(@NonNull TaskEntityDTO input);
	public abstract @NonNull TaskEntityDTO map(@NonNull TaskEntityJPA input);

	public Optional<TaskEntityJPA> getFromContext(TaskEntityDTO dto   ) { return Optional.ofNullable(CONTEXT.get(dto   , TaskEntityJPA.class)); }
	public Optional<TaskEntityDTO> getFromContext(TaskEntityJPA entity) { return Optional.ofNullable(CONTEXT.get(entity, TaskEntityDTO.class)); }

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskGroupEntityJPA source, @MappingTarget TaskGroupEntityDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskGroupEntityJPA source, @MappingTarget TaskGroupEntityDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupEntityJPA target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupEntityJPA target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskEntityJPA source, @MappingTarget TaskEntityDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskEntityJPA source, @MappingTarget TaskEntityDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskEntityDTO source, @MappingTarget TaskEntityJPA target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskEntityDTO source, @MappingTarget TaskEntityJPA target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
	}

	@ObjectFactory
	@NonNull
	TaskGroupEntityJPA lookupOrCreate(@NonNull TaskGroupEntityDTO input)
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

	@ObjectFactory
	@NonNull
	TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupEntityJPA input)
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

	@ObjectFactory
	@NonNull
	TaskEntityJPA lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		TaskEntityJPA result = CONTEXT.get(input, TaskEntityJPA.class);
		if (result == null)
		{
			TaskGroupEntityJPA taskGroupEntity = lookupOrCreate(input.taskGroup());
			result = new TaskEntityJPA(taskGroupEntity, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskEntityDTO lookupOrCreate(@NonNull TaskEntityJPA input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO taskGroupDTO = lookupOrCreate(input.taskGroup());
			result = new TaskEntityDTO(taskGroupDTO, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}