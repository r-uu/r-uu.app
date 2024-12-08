package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@Mapper
public abstract class Map_TaskGroup_Bean_FXBean
{
	public final static Map_TaskGroup_Bean_FXBean INSTANCE = Mappers.getMapper(Map_TaskGroup_Bean_FXBean.class);

	public abstract @NonNull TaskGroupFXBean map(@NonNull TaskGroupBean   input);
	public abstract @NonNull TaskGroupBean   map(@NonNull TaskGroupFXBean input);

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
	@ObjectFactory
	@NonNull
	TaskGroupBean lookupOrCreate(@NonNull TaskGroupFXBean input)
	{
		return ObjectFactories.INSTANCE.lookupOrCreateTaskGroupBean(input);
	}
	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull
	TaskGroupFXBean lookupOrCreate(@NonNull TaskGroupBean input)
	{
		return ObjectFactories.INSTANCE.lookupOrCreateTaskGroupFXBean(input);
	}
}