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

import java.util.Optional;

@Slf4j
@org.mapstruct.Mapper
public abstract class Mapper
{
	public final static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private final static ReferenceCycleTracking CONTEXT = new ReferenceCycleTracking();

	public @NonNull TaskGroupBean map(@NonNull TaskGroupEntityDTO source)
	{
		return map(new TaskGroupDTOMapStruct(source));
	}
	public @NonNull TaskBean      map(@NonNull TaskEntityDTO      source)
	{
		return map(new TaskDTOMapStruct(source));
	}

	public abstract @NonNull TaskGroupDTOMapStruct map(@NonNull TaskGroupBean         source);
	public abstract @NonNull TaskGroupBean         map(@NonNull TaskGroupDTOMapStruct source);

	public abstract @NonNull TaskDTOMapStruct map(@NonNull TaskBean         source);
	public abstract @NonNull TaskBean         map(@NonNull TaskDTOMapStruct source);

	Optional<TaskBean>      getFromContext(TaskEntityDTO dto ) { return Optional.ofNullable(CONTEXT.get(dto , TaskBean.class     )); }
	Optional<TaskEntityDTO> getFromContext(TaskBean      bean) { return Optional.ofNullable(CONTEXT.get(bean, TaskEntityDTO.class)); }

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

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskGroupBean source, @MappingTarget TaskGroupDTOMapStruct target)
	{
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskGroupBean source, @MappingTarget TaskGroupDTOMapStruct target)
	{
		target.afterMapping(source);  // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(TaskBean source, @MappingTarget TaskDTOMapStruct target)
	{
		target.beforeMapping(source);
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(TaskBean source, @MappingTarget TaskDTOMapStruct target)
	{
		target.afterMapping(source);
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
	@NonNull TaskGroupBean lookupOrCreate(@NonNull TaskGroupDTOMapStruct input)
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

	/** object factory will be called by mapstruct */
	@ObjectFactory
	@NonNull TaskGroupEntityDTO lookupOrCreate(@NonNull TaskGroupBean input)
	{
		TaskGroupEntityDTO result = CONTEXT.get(input, TaskGroupEntityDTO.class);
		if (result == null)
		{
			result = new TaskGroupEntityDTO(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull TaskBean lookupOrCreate(@NonNull TaskDTOMapStruct input)
	{
		TaskBean result = CONTEXT.get(input, TaskBean.class);
		if (result == null)
		{
			TaskGroupBean taskGroupBean = lookupOrCreate(new TaskGroupDTOMapStruct(input.taskGroup()));
			result = new TaskBean(taskGroupBean, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory
	@NonNull TaskEntityDTO lookupOrCreate(@NonNull TaskBean input)
	{
		TaskEntityDTO result = CONTEXT.get(input, TaskEntityDTO.class);
		if (result == null)
		{
			TaskGroupEntityDTO taskGroupEntityDTO = lookupOrCreate(input.taskGroup());
			result = new TaskEntityDTO(taskGroupEntityDTO, input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	/**
	 * The purpose of this class is to provide a {@link #beforeMapping(TaskGroupBean)} method that takes a {@link
	 * TaskBean} parameter. Alternatively another {@link #beforeMapping(TaskGroupBean)} method could be defined
	 * in {@link TaskGroupEntityDTO} but with this approach {@link TaskEntityDTO} can be kept independent from this
	 * module / package.
	 */
	static class TaskGroupDTOMapStruct extends TaskGroupEntityDTO
	{
		private @NonNull TaskGroupEntityDTO taskGroupEntityDTO;

		TaskGroupDTOMapStruct(@NonNull TaskGroupEntityDTO taskGroupEntityDTO)
		{
			this.taskGroupEntityDTO = taskGroupEntityDTO;
		}

		private void beforeMapping(@NonNull TaskGroupBean taskGroup)
		{
			mapIdAndVersion(taskGroup);
			taskGroup.tasks().ifPresent(ts -> ts.forEach(t -> addTask(Mapper.INSTANCE.map(t))));
			// mapping of other fields is done via mapstruct using java-beans accessors
		}
		private void afterMapping (@NonNull TaskGroupBean taskGroup) { }
	}

	/**
	 * The purpose of this class is to provide a {@link #beforeMapping(TaskBean)} method that takes a {@link
	 * TaskBean} parameter. Alternatively another {@link #beforeMapping(TaskBean)} method could be defined
	 * in {@link TaskEntityDTO} but with this approach {@link TaskEntityDTO} can be kept independent from this
	 * module / package.
	 */
	static class TaskDTOMapStruct extends TaskEntityDTO
	{
		private @NonNull TaskEntityDTO taskEntityDTO;

		TaskDTOMapStruct(@NonNull TaskEntityDTO taskEntityDTO)
		{
			this.taskEntityDTO = taskEntityDTO;
		}

		private void beforeMapping(@NonNull TaskBean task)
		{
			mapIdAndVersion(task);

			task.parent().map(t -> parent(Mapper.INSTANCE.map(t)));

			task.children    ().ifPresent(ts -> ts.forEach(t -> addChild      (Mapper.INSTANCE.map(t))));
			task.predecessors().ifPresent(ts -> ts.forEach(t -> addPredecessor(Mapper.INSTANCE.map(t))));
			task.successors  ().ifPresent(ts -> ts.forEach(t -> addSuccessor  (Mapper.INSTANCE.map(t))));
			// mapping of other fields is done via mapstruct using java-beans accessors

//			task.description    ().ifPresent(this::description);
//			task.startEstimated ().ifPresent(this::startEstimated);
//			task.startActual    ().ifPresent(this::startActual);
//			task.finishEstimated().ifPresent(this::finishEstimated);
//			task.finishActual   ().ifPresent(this::finishActual);
//			task.effortEstimated().ifPresent(this::effortEstimated);
//			task.effortActual   ().ifPresent(this::effortActual);
		}
		private void afterMapping (@NonNull TaskBean task) { }
	}
}