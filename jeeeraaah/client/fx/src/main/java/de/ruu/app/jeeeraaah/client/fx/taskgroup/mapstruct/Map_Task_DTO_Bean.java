package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
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
public abstract class Map_Task_DTO_Bean
{
	public final static Map_Task_DTO_Bean INSTANCE = Mappers.getMapper(Map_Task_DTO_Bean.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskEntityDTO      map(@NonNull TaskBean           source);
	public abstract @NonNull TaskBean           map(@NonNull TaskEntityDTO      source);

	Optional<TaskBean>      getFromContext(TaskEntityDTO dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class     )); }
	Optional<TaskEntityDTO> getFromContext(TaskBean      bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskEntityDTO.class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.beforeMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.afterMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		target.beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		target.afterMapping(source);
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskBean lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			TaskGroupBean taskGroupBean = Map_TaskGroup_DTO_Bean.INSTANCE.lookupOrCreate(input.taskGroup());
			result = new TaskBean(taskGroupBean, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskEntityDTO lookupOrCreate(@NonNull TaskBean input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO taskGroupEntityDTO = Map_TaskGroup_DTO_Bean.INSTANCE.lookupOrCreate(input.taskGroup());
			result = new TaskEntityDTO(taskGroupEntityDTO, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}