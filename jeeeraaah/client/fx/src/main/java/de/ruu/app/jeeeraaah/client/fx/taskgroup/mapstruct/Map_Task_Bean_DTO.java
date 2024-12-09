package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskBean} <-> {@link TaskDTO} */
@Mapper public abstract class Map_Task_Bean_DTO
{
	public final static Map_Task_Bean_DTO INSTANCE = Mappers.getMapper(Map_Task_Bean_DTO.class);

	public abstract @NonNull TaskDTO map(@NonNull TaskBean source);

//	Optional<TaskBean> getFromContext(TaskDTO  dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class)); }
//	Optional<TaskDTO>  getFromContext(TaskBean bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskDTO .class)); }

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskDTO target)
	{
		target.beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping  void afterMapping (TaskBean source, @MappingTarget TaskDTO target)
	{
		target.afterMapping(source);
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory @NonNull TaskBean lookupOrCreate(@NonNull TaskDTO  input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskBean(input);
	}
}