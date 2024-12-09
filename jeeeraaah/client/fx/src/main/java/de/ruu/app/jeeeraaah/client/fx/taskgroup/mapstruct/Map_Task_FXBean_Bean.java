package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/** {@link TaskFXBean} <-> {@link TaskBean} */
@Mapper public abstract class Map_Task_FXBean_Bean
{
	public final static Map_Task_FXBean_Bean INSTANCE = Mappers.getMapper(Map_Task_FXBean_Bean.class);

	public abstract @NonNull TaskBean map(@NonNull TaskFXBean input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskFXBean source, @MappingTarget TaskBean target)
	{
		target.beforeMappingFX(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskFXBean source, @MappingTarget TaskBean target)
	{
		target.afterMappingFX(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory @NonNull TaskBean lookupOrCreate(@NonNull TaskFXBean input)
	{
		return ObjectDictionaryAndFactory.INSTANCE.lookupOrCreateTaskBean(input);
	}
//
//	/** object factory will be called by mapstruct */
//	@ObjectFactory
//	@NonNull
//	TaskGroupBean lookupOrCreate(@NonNull TaskGroupFXBean input)
//	{
//		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
//		if (result == null)
//		{
//			result = new TaskGroupBean(input.name());
//			CONTEXT.put(input , result);
//			CONTEXT.put(result, input);
//		}
//		return result;
//	}
//
//	/** object factory will be called by mapstruct */
//	@ObjectFactory
//	@NonNull
//	TaskGroupFXBean lookupOrCreate(@NonNull TaskGroupBean input)
//	{
//		TaskGroupFXBean result = CONTEXT.get(input, TaskGroupFXBean.class);
//		if (result == null)
//		{
//			result = new TaskGroupFXBean(input.name());
//			CONTEXT.put(input , result);
//			CONTEXT.put(result, input);
//		}
//		return result;
//	}
//
//	/** object factory will be called by mapstruct */
//	@ObjectFactory
//	@NonNull
//	TaskBean lookupOrCreate(@NonNull TaskFXBean input)
//	{
//		TaskBean result = CONTEXT.get(input, TaskBean.class);
//		if (result == null)
//		{
//			TaskGroupBean taskGroupBean = lookupOrCreate(input.taskGroup());
//			result = new TaskBean(taskGroupBean, input.name());
//			CONTEXT.put(input , result);
//			CONTEXT.put(result, input);
//		}
//		return result;
//	}
//
//	@ObjectFactory
//	@NonNull
//	TaskFXBean lookupOrCreate(@NonNull TaskBean input)
//	{
//		TaskFXBean result = CONTEXT.get(input, TaskFXBean.class);
//		if (result == null)
//		{
//			TaskGroupFXBean taskGroupFXBean = lookupOrCreate((TaskGroupBean) input.taskGroup());
//			result = new TaskFXBean(taskGroupFXBean, input.name());
//			CONTEXT.put(input , result);
//			CONTEXT.put(result, input);
//		}
//		return result;
//	}
}