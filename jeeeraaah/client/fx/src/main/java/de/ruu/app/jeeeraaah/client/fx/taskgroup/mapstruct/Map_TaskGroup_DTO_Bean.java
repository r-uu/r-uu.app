package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
public abstract class Map_TaskGroup_DTO_Bean
{
	public final static Map_TaskGroup_DTO_Bean INSTANCE = Mappers.getMapper(Map_TaskGroup_DTO_Bean.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupBean      source);
	public abstract @NonNull TaskGroupBean      map(@NonNull TaskGroupEntityDTO source);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.beforeMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.afterMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
		target.beforeMapping(source);
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
		target.afterMapping(source);
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskGroupBean lookupOrCreate(@NonNull TaskGroupEntityDTO input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = new TaskGroupBean(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupBean input)
	{
		TaskGroupEntityDTO result = CONTEXT.get(input, TaskGroupEntityDTO.class);
		if (result == null)
		{
			result = new TaskGroupEntityDTO(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}