package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskGroupBean} <-> {@link TaskGroupFXBean} */
@Mapper public abstract class Map_TaskGroup_Bean_FXBean
{
	public final static Map_TaskGroup_Bean_FXBean INSTANCE = Mappers.getMapper(Map_TaskGroup_Bean_FXBean.class);

	public abstract @NonNull TaskGroupFXBean map(@NonNull TaskGroupBean input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupFXBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupBean source, @MappingTarget TaskGroupFXBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory @NonNull TaskGroupFXBean lookupOrCreate(@NonNull TaskGroupBean input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskGroupFXBean(input);
	}
}