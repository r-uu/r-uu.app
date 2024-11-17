package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.v2;

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

	public abstract @NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean map(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean input);
	public abstract @NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean map(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean input);

	public abstract @NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean map(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean input);
	public abstract @NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean map(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean source, @MappingTarget de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull
	de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean lookupOrCreate(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean input)
	{
		de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean result = CONTEXT.get(input, de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean.class);
		if (result == null)
		{
			result = new de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull
	de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean lookupOrCreate(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupBean input)
	{
		de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean result = CONTEXT.get(input, de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean.class);
		if (result == null)
		{
			result = new de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskGroupFXBean(input.name());
			if (input.tasks().isPresent())
			{
				for (de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean taskBean : input.tasks().get())
				{
					result.addTask(MapperFX.INSTANCE.map(taskBean));
				}
			}
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull
	de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean lookupOrCreate(@NonNull de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean input)
	{
		de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean result = CONTEXT.get(input, de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean.class);
		if (result == null)
		{
			TaskGroupBean taskGroupEntity = lookupOrCreate(input.taskGroup());
			result = new de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskBean(taskGroupEntity, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean lookupOrCreate(@NonNull TaskBean input)
	{
		de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean result = CONTEXT.get(input, de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.TaskFXBean.class);
		if (result == null)
		{
			TaskGroupFXBean taskGroupEntity = lookupOrCreate(input.taskGroup());
			result = new TaskFXBean(taskGroupEntity, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}