package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;

public final class ObjectFactories
{
	public final static ObjectFactories INSTANCE = new ObjectFactories();

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	private ObjectFactories() { }

	/** object factory will be called by mapstruct */
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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

	/// ///////////////////////////////////////////////////////////////////////////////////////

	/** object factory will be called by mapstruct */
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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
//	@ObjectFactory
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