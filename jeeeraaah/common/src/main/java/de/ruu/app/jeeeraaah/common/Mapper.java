package de.ruu.app.jeeeraaah.common;

import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
abstract class Mapper
{
	static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	abstract TaskGroupEntity map(TaskGroupDTO    input);
	abstract TaskGroupDTO    map(TaskGroupEntity input);

	abstract TaskEntity      map(TaskDTO         input);
	abstract TaskDTO         map(TaskEntity      input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntity source, @MappingTarget TaskGroupDTO target)
	{
		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupDTO source, @MappingTarget TaskGroupEntity target)
	{
		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntity source, @MappingTarget TaskDTO target)
	{
		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskDTO source, @MappingTarget TaskEntity target)
	{
		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	@ObjectFactory @NonNull TaskGroupEntity lookupOrCreate(@NonNull TaskGroupDTO input)
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

	@ObjectFactory @NonNull TaskGroupDTO lookupOrCreate(@NonNull TaskGroupEntity input)
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

	@ObjectFactory @NonNull TaskEntity lookupOrCreate(@NonNull TaskDTO input)
	{
		TaskEntity result = CONTEXT.get(input, TaskEntity.class);
		if (result == null)
		{
			TaskGroupEntity taskGroup = CONTEXT.get(input.taskGroup(), TaskGroupEntity.class);
			if (taskGroup == null)
					taskGroup = new TaskGroupEntity(input.taskGroup().name());
			result = new TaskEntity(taskGroup, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory @NonNull TaskDTO lookupOrCreate(@NonNull TaskEntity input)
	{
		TaskDTO result = CONTEXT.get(input, TaskDTO.class);
		if (result == null)
		{
			result = new TaskDTO(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}