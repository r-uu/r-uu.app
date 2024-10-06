package de.ruu.app.jeeeraaah.common.jpadto;

import de.ruu.app.jeeeraaah.common.dto.TaskDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupDTO;
import de.ruu.app.jeeeraaah.common.jpa.TaskEntity;
import de.ruu.app.jeeeraaah.common.jpa.TaskGroupEntity;
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

	public abstract @NonNull TaskGroupEntity map(@NonNull TaskGroupDTO input);
	public abstract @NonNull TaskGroupDTO    map(@NonNull TaskGroupEntity input);

	public abstract @NonNull TaskEntity map(@NonNull TaskDTO input);
	public abstract @NonNull TaskDTO    map(@NonNull TaskEntity input);

	public Optional<TaskEntity> getFromContext(TaskDTO    dto   ) { return Optional.ofNullable(CONTEXT.get(dto   , TaskEntity.class)); }
	public Optional<TaskDTO>    getFromContext(TaskEntity entity) { return Optional.ofNullable(CONTEXT.get(entity, TaskDTO   .class)); }

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskGroupEntity source, @MappingTarget TaskGroupDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskGroupEntity source, @MappingTarget TaskGroupDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskGroupDTO source, @MappingTarget TaskGroupEntity target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskGroupDTO source, @MappingTarget TaskGroupEntity target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskEntity source, @MappingTarget TaskDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskEntity source, @MappingTarget TaskDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		log.debug("");
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskDTO source, @MappingTarget TaskEntity target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskDTO source, @MappingTarget TaskEntity target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		log.debug("");
	}

	@ObjectFactory
	@NonNull
	TaskGroupEntity lookupOrCreate(@NonNull TaskGroupDTO input)
	{
		TaskGroupEntity result = CONTEXT.get(input, TaskGroupEntity.class);
		if (result == null)
		{
			result = new TaskGroupEntity(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskGroupDTO lookupOrCreate(@NonNull TaskGroupEntity input)
	{
		TaskGroupDTO result = CONTEXT.get(input, TaskGroupDTO.class);
		if (result == null)
		{
			result = new TaskGroupDTO(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskEntity lookupOrCreate(@NonNull TaskDTO input)
	{
		TaskEntity result = CONTEXT.get(input, TaskEntity.class);
		if (result == null)
		{
			TaskGroupEntity taskGroupEntity = lookupOrCreate(input.taskGroup());
			result = new TaskEntity(taskGroupEntity, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskDTO lookupOrCreate(@NonNull TaskEntity input)
	{
		TaskDTO result = CONTEXT.get(input, TaskDTO.class);
		if (result == null)
		{
			TaskGroupDTO taskGroupDTO = lookupOrCreate(input.taskGroup());
			result = new TaskDTO(taskGroupDTO, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}