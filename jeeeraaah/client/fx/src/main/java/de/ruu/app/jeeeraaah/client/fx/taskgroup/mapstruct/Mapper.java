package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
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
public abstract class Mapper
{
	public final static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public abstract @NonNull TaskGroupEntityDTO map(@NonNull TaskGroupBean       input);
	public abstract @NonNull TaskGroupBean      map(@NonNull TaskGroupEntityDTO  input);

	public abstract @NonNull TaskEntityDTO map(@NonNull TaskBean      input);
	public abstract @NonNull TaskBean      map(@NonNull TaskEntityDTO input);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupEntityDTO source, @MappingTarget TaskGroupBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	private static class TaskGroupDTOMapStruct extends TaskGroupEntityDTO
	{
//		private TaskGroupEntityDTO delegate;
//
//		private TaskGroupDTOMapStruct(@NonNull TaskGroupEntityDTO delegate) { this.delegate = delegate; }

		private void beforeMapping(@NonNull TaskGroupBean taskGroupBean) { mapIdAndVersion(taskGroupBean); }
		private void afterMapping (@NonNull TaskGroupBean taskGroupBean) { }
	}

	private static class TaskDTOMapStruct extends TaskEntityDTO
	{
		private void beforeMapping(@NonNull TaskBean taskBean) { mapIdAndVersion(taskBean); }
		private void afterMapping (@NonNull TaskBean taskBean) { }
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
//		TaskGroupDTOMapStruct delegate = new TaskGroupDTOMapStruct(target);
		TaskGroupDTOMapStruct delegate = new TaskGroupDTOMapStruct();

		delegate.beforeMapping(source); // invoke callback for mapping
//		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupBean source, @MappingTarget TaskGroupEntityDTO target)
	{
		TaskGroupDTOMapStruct delegate = new TaskGroupDTOMapStruct();

		delegate.afterMapping(source);  // invoke callback for mapping
//		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		new TaskDTOMapStruct().beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskBean source, @MappingTarget TaskEntityDTO target)
	{
		new TaskDTOMapStruct().afterMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskEntityDTO source, @MappingTarget TaskBean target)
	{
		target.afterMapping(source); // invoke callback for mapping
	}

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull
	TaskGroupBean lookupOrCreate(@NonNull TaskGroupEntityDTO input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = new TaskGroupBean(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupBean input)
	{
		TaskGroupEntityDTO result = CONTEXT.get(input, TaskGroupEntityDTO.class);
		if (result == null)
		{
			result = new TaskGroupEntityDTO(input.name());
			if (input.tasks().isPresent())
			{
				for (TaskBean taskBean : input.tasks().get())
				{
					result.addTask(Mapper.INSTANCE.map(taskBean));
				}
			}
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull
	TaskBean lookupOrCreate(@NonNull TaskEntityDTO input)
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
	TaskEntityDTO lookupOrCreate(@NonNull TaskBean input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO taskGroupEntityDTO = CONTEXT.get(input.taskGroup(), TaskGroupEntityDTO.class);
			if (taskGroupEntityDTO == null)
			{
				taskGroupEntityDTO = new TaskGroupEntityDTO(input.taskGroup().name());
			}
			result = new TaskEntityDTO(taskGroupEntityDTO, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}