package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskDTO} <-> {@link TaskBean} */
@Mapper public abstract class Map_Task_DTO_Bean
{
	public final static Map_Task_DTO_Bean INSTANCE = Mappers.getMapper(Map_Task_DTO_Bean.class);

	public abstract @NonNull TaskBean map(@NonNull TaskDTO source);

//	Optional<TaskBean> getFromContext(TaskDTO  dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class)); }
//	Optional<TaskDTO>  getFromContext(TaskBean bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskDTO .class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskDTO source, @MappingTarget TaskBean target)
	{
		target.beforeMappingDTO(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskDTO source, @MappingTarget TaskBean target)
	{
		target.afterMappingDTO(source); // invoke callback for mapping
	}

	@ObjectFactory @NonNull TaskDTO  lookupOrCreate(@NonNull TaskBean input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskDTO(input);
	}
}