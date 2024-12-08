package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskGroupDTO} <-> {@link TaskGroupBean} */
@Slf4j
@Mapper
public abstract class Map_TaskGroup_Bean_DTO
{
	public final static Map_TaskGroup_Bean_DTO INSTANCE = Mappers.getMapper(Map_TaskGroup_Bean_DTO.class);

	public abstract @NonNull TaskGroupDTO map(@NonNull TaskGroupBean input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupDTO target)
	{
		target.beforeMapping(source);
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupBean source, @MappingTarget TaskGroupDTO target)
	{
		target.afterMapping(source);
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory @NonNull TaskGroupBean lookupOrCreate(@NonNull TaskGroupDTO  input)
	{
		return ObjectFactories.INSTANCE.lookupOrCreateTaskGroupBean(input);
	}
}