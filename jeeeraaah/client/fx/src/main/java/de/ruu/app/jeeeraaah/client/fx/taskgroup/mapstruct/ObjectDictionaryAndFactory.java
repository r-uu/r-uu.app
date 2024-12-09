package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;

import java.util.Optional;

public final class ObjectDictionaryAndFactory
{
	public final static ObjectDictionaryAndFactory INSTANCE = new ObjectDictionaryAndFactory();

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	private ObjectDictionaryAndFactory() { }

	@NonNull Optional<TaskDTO> lookupTaskDTO(TaskEntityDTO task) { return Optional.of(CONTEXT.get(task, TaskDTO.class)); }

	@NonNull Optional<TaskDTO> lookupTaskDTO(TaskBean task) { return Optional.of(CONTEXT.get(task, TaskDTO.class)); }

	@NonNull Optional<TaskBean> lookupTaskBean(TaskDTO task) { return Optional.of(CONTEXT.get(task, TaskBean.class)); }

	/** object factory will be called by mapstruct */
	@NonNull TaskGroupEntityDTO lookupOrCreateTaskGroupEntityDTO(@NonNull TaskGroupDTO input)
	{
		TaskGroupEntityDTO result = CONTEXT.get(input, TaskGroupEntityDTO.class);
		if (result == null)
		{
			result = new TaskGroupEntityDTO(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskGroupDTO lookupOrCreateTaskGroupDTO(@NonNull TaskGroupEntityDTO input)
	{
		TaskGroupDTO result = CONTEXT.get(input, TaskGroupDTO.class);
		if (result == null)
		{
			result = new TaskGroupDTO(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskGroupDTO lookupOrCreateTaskGroupDTO(@NonNull TaskGroupBean input)
	{
		TaskGroupDTO result = CONTEXT.get(input, TaskGroupDTO.class);
		if (result == null)
		{
			result = new TaskGroupDTO(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskGroupBean lookupOrCreateTaskGroupBean(@NonNull TaskGroupDTO input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = new TaskGroupBean(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskGroupBean lookupOrCreateTaskGroupBean(@NonNull TaskGroupFXBean input)
	{
		TaskGroupBean result = CONTEXT.get(input, TaskGroupBean.class);
		if (result == null)
		{
			result = new TaskGroupBean(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
	/** object factory will be called by mapstruct */
	@NonNull TaskGroupFXBean lookupOrCreateTaskGroupFXBean(@NonNull TaskGroupBean input)
	{
		TaskGroupFXBean result = CONTEXT.get(input, TaskGroupFXBean.class);
		if (result == null)
		{
			result = new TaskGroupFXBean(input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskEntityDTO lookupOrCreateTaskEntityDTO(@NonNull TaskDTO input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO group = lookupOrCreateTaskGroupEntityDTO(input.taskGroup());
			result = new TaskEntityDTO(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskDTO lookupOrCreateTaskDTO(@NonNull TaskEntityDTO input)
	{
		TaskDTO result = CONTEXT.get(input, TaskDTO.class);
		if (result == null)
		{
			TaskGroupDTO group = lookupOrCreateTaskGroupDTO(input.taskGroup());
			result = new TaskDTO(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskDTO lookupOrCreateTaskDTO(@NonNull TaskBean input)
	{
		TaskDTO result = CONTEXT.get(input, TaskDTO.class);
		if (result == null)
		{
			TaskGroupDTO group = lookupOrCreateTaskGroupDTO(input.taskGroup());
			result = new TaskDTO(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskBean lookupOrCreateTaskBean(@NonNull TaskDTO input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			TaskGroupBean group = lookupOrCreateTaskGroupBean(input.taskGroup());
			result = new TaskBean(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/** object factory will be called by mapstruct */
	@NonNull TaskBean lookupOrCreateTaskBean(@NonNull TaskFXBean input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			TaskGroupBean group = lookupOrCreateTaskGroupBean(input.taskGroup());
			result = new TaskBean(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
	/** object factory will be called by mapstruct */
	@NonNull TaskFXBean lookupOrCreateTaskFXBean(@NonNull TaskBean input)
	{
		TaskFXBean result = CONTEXT.get(input, TaskFXBean.class);
		if (result == null)
		{
			TaskGroupFXBean group = lookupOrCreateTaskGroupFXBean(input.taskGroup());
			result = new TaskFXBean(group, input.name());
			CONTEXT.put(input , result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}