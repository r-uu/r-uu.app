package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.client.fx.task.TaskFXBean;
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
public abstract class MapperFX
{
	public final static MapperFX INSTANCE = Mappers.getMapper(MapperFX.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupBean   map(@NonNull TaskGroupFXBean input);
	public abstract @NonNull TaskGroupFXBean map(@NonNull TaskGroupBean   input);

	public abstract @NonNull TaskBean   map(@NonNull TaskFXBean input);
	public abstract @NonNull TaskFXBean map(@NonNull TaskBean   input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupFXBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupBean source, @MappingTarget TaskGroupFXBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskFXBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskBean source, @MappingTarget TaskFXBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskFXBean source, @MappingTarget TaskBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskFXBean source, @MappingTarget TaskBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupFXBean source, @MappingTarget TaskGroupBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupFXBean source, @MappingTarget TaskGroupBean target)
	{
//		log.debug("\nsource\n{}\ntarget\n{}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	@ObjectFactory
	@NonNull
	TaskGroupBean lookupOrCreate(@NonNull TaskGroupFXBean input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = lookupOrCreate(input);
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskGroupFXBean lookupOrCreate(@NonNull TaskGroupBean input)
	{
		TaskGroupFXBean result = CONTEXT.get(input, TaskGroupFXBean.class);
		if (result == null)
		{
			result = new TaskGroupFXBean(input);
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskBean lookupOrCreate(@NonNull TaskFXBean input)
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
	TaskFXBean lookupOrCreate(@NonNull TaskBean input)
	{
		TaskFXBean result = CONTEXT.get(input, TaskFXBean.class);
		if (result == null)
		{
			result = new TaskFXBean(input);
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}