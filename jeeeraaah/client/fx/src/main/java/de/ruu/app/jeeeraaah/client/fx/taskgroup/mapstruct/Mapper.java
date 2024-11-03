package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

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
public abstract class Mapper
{
	public final static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupDTO  map(@NonNull TaskGroupBean input);
	public abstract @NonNull TaskGroupBean map(@NonNull TaskGroupDTO  input);

	public abstract @NonNull TaskDTO  map(@NonNull TaskBean input);
	public abstract @NonNull TaskBean map(@NonNull TaskDTO input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupDTO source, @MappingTarget TaskGroupBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupDTO source, @MappingTarget TaskGroupBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupDTO target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupBean source, @MappingTarget TaskGroupDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskBean source, @MappingTarget TaskDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskBean source, @MappingTarget TaskDTO target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source);
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@BeforeMapping
	void beforeMapping(TaskDTO source, @MappingTarget TaskBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/**
	 * annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called
	 */
	@AfterMapping
	void afterMapping(TaskDTO source, @MappingTarget TaskBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
	}

	@ObjectFactory
	@NonNull
	TaskGroupBean lookupOrCreate(@NonNull TaskGroupDTO input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = new TaskGroupBean(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskGroupDTO lookupOrCreate(@NonNull TaskGroupBean input)
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
	TaskBean lookupOrCreate(@NonNull TaskDTO input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			result = new TaskBean();
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskDTO lookupOrCreate(@NonNull TaskBean input)
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