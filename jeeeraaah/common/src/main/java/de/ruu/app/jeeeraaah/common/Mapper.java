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

	abstract TaskEntity map(TaskDTO    input);
	abstract TaskDTO    map(TaskEntity input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntity source, @MappingTarget TaskDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskDTO source, @MappingTarget TaskEntity target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	@ObjectFactory @NonNull TaskEntity lookupOrCreate(@NonNull TaskDTO input)
	{
		TaskEntity result = CONTEXT.get(input, TaskEntity.class);
		if (result == null)
		{
			result = new TaskEntity(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
			// handle recursive data structure
			if (input.parent().isPresent())
			{
				TaskDTO parent = input.parent().get();
				if (parent == input)
				{
					result.parent(CONTEXT.get(parent, TaskEntity.class));
				}
				else
				{
					result.parent(new TaskEntity(input.name()));
				}
			}
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
			// handle recursive data structure
			if (input.parent().isPresent())
			{
				TaskEntity parent = input.parent().get();
				if (parent == input)
				{
					result.parent(CONTEXT.get(parent, TaskDTO.class));
				}
				else
				{
					result.parent(new TaskDTO(input.name()));
				}
			}
		}
		return result;
	}
}