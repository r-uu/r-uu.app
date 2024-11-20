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
public abstract class Mapper
{
	public final static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupBean      source);
	public abstract @NonNull TaskGroupBean      map(@NonNull TaskGroupEntityDTO source);

	public abstract @NonNull TaskEntityDTO      map(@NonNull TaskBean           source);
	public abstract @NonNull TaskBean           map(@NonNull TaskEntityDTO      source);

	Optional<TaskBean>      getFromContext(TaskEntityDTO dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class     )); }
//	Optional<TaskEntityDTO> getFromContext(TaskBean      bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskEntityDTO.class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
		lookupOrCreate(target).beforeMapping(source);
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
		lookupOrCreate(target).afterMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		lookupOrCreate(target).beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		lookupOrCreate(target).afterMapping(source);
	}

	/** object factory will be called by mapstruct TODO ambiguous */
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

	/** object factory will be called by mapstruct TODO ambiguous */
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

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskBean lookupOrCreate(@NonNull TaskEntityDTO input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			TaskGroupBean taskGroupBean = lookupOrCreate(input.taskGroup());
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
			TaskGroupEntityDTO taskGroupEntityDTO = lookupOrCreate(input.taskGroup());
			result = new TaskEntityDTO(taskGroupEntityDTO, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}