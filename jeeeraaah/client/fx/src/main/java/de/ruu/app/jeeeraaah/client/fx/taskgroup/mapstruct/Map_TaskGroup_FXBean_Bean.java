package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/** {@link TaskGroupFXBean} <-> {@link TaskGroupBean} */
@Mapper public abstract class Map_TaskGroup_FXBean_Bean
{
	public final static Map_TaskGroup_FXBean_Bean INSTANCE = Mappers.getMapper(Map_TaskGroup_FXBean_Bean.class);

	public abstract @NonNull TaskGroupBean map(@NonNull TaskGroupFXBean input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupFXBean source, @MappingTarget TaskGroupBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupFXBean source, @MappingTarget TaskGroupBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory @NonNull TaskGroupBean lookupOrCreate(@NonNull TaskGroupFXBean input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskGroupBean(input);
	}
}