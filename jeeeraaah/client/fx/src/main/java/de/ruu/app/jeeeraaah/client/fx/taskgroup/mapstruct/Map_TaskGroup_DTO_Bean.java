package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
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
public abstract class Map_TaskGroup_DTO_Bean
{
	public final static Map_TaskGroup_DTO_Bean INSTANCE = Mappers.getMapper(Map_TaskGroup_DTO_Bean.class);

	public abstract @NonNull TaskGroupBean map(@NonNull TaskGroupDTO  source);
	public abstract @NonNull TaskGroupDTO  map(@NonNull TaskGroupBean source);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupDTO source, @MappingTarget TaskGroupBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}
	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskGroupDTO source, @MappingTarget TaskGroupBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

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

	@ObjectFactory @NonNull TaskGroupBean lookupOrCreate(@NonNull TaskGroupDTO  input)
	{
		return ObjectFactories.INSTANCE.lookupOrCreateTaskGroupBean(input);
	}
	@ObjectFactory @NonNull TaskGroupDTO  lookupOrCreate(@NonNull TaskGroupBean input)
	{
		return ObjectFactories.INSTANCE.lookupOrCreateTaskGroupDTO(input);
	}
}