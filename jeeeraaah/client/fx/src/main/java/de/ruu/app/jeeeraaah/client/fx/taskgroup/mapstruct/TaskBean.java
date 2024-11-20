package de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct;

import de.ruu.app.jeeeraaah.common.dto.TaskEntityDTO;
import de.ruu.app.jeeeraaah.common.jpadto.TaskEntity;
import de.ruu.lib.jpa.core.AbstractDTO;
import de.ruu.lib.jpa.core.AbstractEntity;
import de.ruu.lib.jpa.core.Entity;
import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;

import java.util.Optional;

/** JavaBean for implementing business logic */
public class TaskBean implements TaskEntity<TaskGroupBean, TaskBean>
{
	public TaskBean(TaskGroupBean taskGroupBean, @NonNull String name) { super(taskGroupBean, name); }

	//////////////////////
	// mapstruct callbacks
	//////////////////////

	@BeforeMapping void beforeMapping(@NonNull TaskEntityDTO source)
	{
		mapIdAndVersion(new EntitySimple(source));

		taskGroup(Mapper.INSTANCE.map(source.taskGroup()));
//		name(source.name());
		// mapping of other fields is done via mapstruct using java-beans accessors

		source.description    ().ifPresent(super::description);
		source.startEstimated ().ifPresent(super::startEstimated);
		source.startActual    ().ifPresent(super::startActual);
		source.finishEstimated().ifPresent(super::finishEstimated);
		source.finishActual   ().ifPresent(super::finishActual);
		source.effortEstimated().ifPresent(super::effortEstimated);
		source.effortActual   ().ifPresent(super::effortActual);

		source.parent      ().ifPresent(                                    this::lookupOrMapParent);
		source.predecessors().ifPresent(ts -> ts.forEach(this::lookupOrMapPredecessor));
		source.successors  ().ifPresent(ts -> ts.forEach(this::lookupOrMapSuccessor));
		source.children    ().ifPresent(ts -> ts.forEach(this::lookupOrMapChild));
	}
	@AfterMapping void afterMapping (@NonNull TaskEntityDTO source) { }

	public @NonNull TaskEntityDTO toDTOSource() { return Mapper.INSTANCE.map(this); }

	@BeforeMapping public void beforeMapping(@NonNull TaskFXBean source)
	{
		mapIdAndVersion(new EntitySimple(source));

		taskGroup(MapperFX.INSTANCE.map(source.taskGroup()));
	//	name(source.name());

		source.description    ().ifPresent(super::description);
		source.startEstimated ().ifPresent(super::startEstimated);
		source.startActual    ().ifPresent(super::startActual);
		source.finishEstimated().ifPresent(super::finishEstimated);
		source.finishActual   ().ifPresent(super::finishActual);
		source.effortEstimated().ifPresent(super::effortEstimated);
		source.effortActual   ().ifPresent(super::effortActual);

		source.parent().map(t -> parent(MapperFX.INSTANCE.map(t)));

		source.children    ().ifPresent(ts -> ts.forEach(t -> addChild      (MapperFX.INSTANCE.map(t))));
		source.predecessors().ifPresent(ts -> ts.forEach(t -> addPredecessor(MapperFX.INSTANCE.map(t))));
		source.successors  ().ifPresent(ts -> ts.forEach(t -> addSuccessor  (MapperFX.INSTANCE.map(t))));
	}
	@AfterMapping public void afterMapping (@NonNull TaskFXBean source) { }

	public @NonNull TaskFXBean toFXSource() { return MapperFX.INSTANCE.map(this); }

	private void lookupOrMapParent(@NonNull TaskEntityDTO parent)
	{
		Optional<TaskBean> optionalParent = Mapper.INSTANCE.getFromContext(parent);
		optionalParent.ifPresentOrElse
		(
				(p) -> parent(p),
				()            -> parent(Mapper.INSTANCE.map(parent))
		);
	}

	private void lookupOrMapChild(@NonNull TaskEntityDTO child)
	{
		Optional<TaskBean> optionalChild = Mapper.INSTANCE.getFromContext(child);
		optionalChild.ifPresentOrElse
		(
				(c) -> addChild(c),
				()            -> addChild(Mapper.INSTANCE.map(child))
		);
	}

	private void lookupOrMapPredecessor(@NonNull TaskEntityDTO predecessor)
	{
		Optional<TaskBean> optionalPredecessor = Mapper.INSTANCE.getFromContext(predecessor);
		optionalPredecessor.ifPresentOrElse
		(
				(p) -> addPredecessor(p),
				()            -> addChild(Mapper.INSTANCE.map(predecessor))
		);
	}

	private void lookupOrMapSuccessor(@NonNull TaskEntityDTO successor)
	{
		Optional<TaskBean> optionalPredecessor = Mapper.INSTANCE.getFromContext(successor);
		optionalPredecessor.ifPresentOrElse
		(
				(s) -> addPredecessor(s),
				()            -> addChild(Mapper.INSTANCE.map(successor))
		);
	}

	private class AbstractEntitySimple extends AbstractEntity<AbstractDTOSimple> { }
	private class AbstractDTOSimple    extends AbstractDTO<AbstractEntitySimple> { }
	private class EntitySimple         extends AbstractEntity<AbstractDTOSimple>
	{
		public EntitySimple(Entity<Long> entity) { super(entity); }
	}
}